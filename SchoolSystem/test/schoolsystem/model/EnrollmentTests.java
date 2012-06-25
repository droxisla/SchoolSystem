package schoolsystem.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.*;

import org.junit.*;

import schoolsystem.model.EnrollmentForm.EnrollmentFormBuilder;
import schoolsystem.model.schedule.AcademicTerm;
import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;
import schoolsystem.model.schedule.ScheduleDays;
import schoolsystem.model.schedule.ScheduleTimes;

public class EnrollmentTests {

	private static final int MAX_UNITS = 3;
	private Curriculum curriculum;
	private AcademicTerm academicTerm;

	@Before
	public void createFixture() throws Exception {
		academicTerm = AcademicTerm.academicTermAfterCurrent();
		SectionManager.getInstance().reset();
		curriculum = Curriculum.BS_COMPUTER_SCIENCE;
	}
	
	private Section getFirstSubjectSection() throws ScheduleConflictException {
		Subject subject = curriculum.getSubjects().get(0);
		return createSection("A", subject, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);
	}

	private Section createSection(String sectionName, Subject subject, ScheduleDays scheduleDays,
			ScheduleTimes scheduleTimes) throws ScheduleConflictException {
		Schedule schedule = new Schedule(academicTerm, scheduleDays, scheduleTimes);
		Teacher teacher = new Teacher(1, "John Doe");
		return new Section(sectionName, subject, schedule, teacher);
	}

	@Test
	public void enrollEligibleStudentInSectionWithNoPrereq() throws SectionFullException, IneligibleStudentException,
			SubjectUnitsRestrictionException, ScheduleConflictException, UnsatisfiedPrerequisiteException {
		Student student = new Student(1, StudentStatus.GRADUATING, curriculum);
		Section section = getFirstSubjectSection();
		student.getEnrollmentFormBuilder().addSection(section).enroll();
		assertEquals(1, student.getNumEnrollmentForms());
	}

	@Test(expected = UnsatisfiedPrerequisiteException.class)
	public void enrollNewStudentsWithUnsatisfiedPrereq() throws SectionFullException, IneligibleStudentException,
			SubjectUnitsRestrictionException, ScheduleConflictException, UnsatisfiedPrerequisiteException {
		Student student = new Student(1, StudentStatus.NEW, curriculum);
		enrollStudentWithPrereq(student);
	}

//	@Test(expected = UnsatisfiedPrerequisiteException.class)
//	public void enrollContinuingStudentsWithUnsatisfiedPrereq() throws SectionFullException,
//			IneligibleStudentException, SubjectUnitsRestrictionException, ScheduleConflictException,
//			UnsatisfiedPrerequisiteException {
//		Student student = new Student(1, StudentStatus.CONTINUING, curriculum);
//		enrollStudentWithPrereq(student);
//	}
//
//	@Test(expected = UnsatisfiedPrerequisiteException.class)
//	public void enrollProbationaryStudentsWithUnsatisfiedPrereq() throws SectionFullException,
//			IneligibleStudentException, SubjectUnitsRestrictionException, ScheduleConflictException,
//			UnsatisfiedPrerequisiteException {
//		Student student = new Student(1, StudentStatus.PROBATIONARY, curriculum);
//		enrollStudentWithPrereq(student);
//	}

	private void enrollStudentWithPrereq(Student student) throws ScheduleConflictException, SectionFullException,
			IneligibleStudentException, SubjectUnitsRestrictionException, UnsatisfiedPrerequisiteException {
		Subject subjectWithPrereq = getSubjectWithPrereq();

		Section sectionWithPrereq = createSection("C", subjectWithPrereq, ScheduleDays.WED_AND_SAT,
				ScheduleTimes.FROM_1600_TO_1730);

		EnrollmentFormBuilder enrollmentFormBuilder = student.getEnrollmentFormBuilder();

		StudentStatus studentStatus = student.getStatus();
		addUnitsToEnrollmentForm(studentStatus.getMinUnits(), enrollmentFormBuilder);

		enrollmentFormBuilder.addSection(sectionWithPrereq).enroll();
	}

	private Subject getSubjectWithPrereq() {
		Subject subjectWithPrereq = null;
		for (Subject subj : curriculum.getSubjects()) {
			if (subj.hasPrerequisites()) {
				subjectWithPrereq = subj;
				break;
			}
		}

		assertNotNull(subjectWithPrereq);
		return subjectWithPrereq;
	}

	@Test(expected = IneligibleStudentException.class)
	public void enrollIneligibleStudent() throws SectionFullException, IneligibleStudentException {
		Student ineligibleStudent = new Student(2, StudentStatus.INELIGIBLE, Curriculum.BS_COMPUTER_SCIENCE);
		ineligibleStudent.getEnrollmentFormBuilder();
	}

	@Test(expected = IneligibleStudentException.class)
	public void enrollGraduatedStudent() throws SectionFullException, IneligibleStudentException {
		Student graduatedStudent = new Student(2, StudentStatus.GRADUATE, Curriculum.BS_COMPUTER_SCIENCE);
		graduatedStudent.getEnrollmentFormBuilder();
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void newStudentBelowMinimumUnits() throws IneligibleStudentException, ScheduleConflictException,
			SectionFullException, SubjectUnitsRestrictionException, UnsatisfiedPrerequisiteException {
		StudentStatus studentStatus = StudentStatus.NEW;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMinUnits() - MAX_UNITS);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void newStudentExceededMaximumUnits() throws IneligibleStudentException, ScheduleConflictException,
			SectionFullException, SubjectUnitsRestrictionException, UnsatisfiedPrerequisiteException {
		StudentStatus studentStatus = StudentStatus.NEW;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMaxUnits() + 1);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void continuingStudentExceededMaximumUnits() throws IneligibleStudentException, ScheduleConflictException,
			SectionFullException, SubjectUnitsRestrictionException, UnsatisfiedPrerequisiteException {
		StudentStatus studentStatus = StudentStatus.CONTINUING;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMaxUnits() + 1);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void continuingStudentBelowMinimumUnits() throws IneligibleStudentException, ScheduleConflictException,
			SectionFullException, SubjectUnitsRestrictionException, UnsatisfiedPrerequisiteException {
		StudentStatus studentStatus = StudentStatus.CONTINUING;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMinUnits() - MAX_UNITS);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void probationaryStudentExceededMaximumUnits() throws IneligibleStudentException, ScheduleConflictException,
			SectionFullException, SubjectUnitsRestrictionException, UnsatisfiedPrerequisiteException {
		StudentStatus studentStatus = StudentStatus.CONTINUING;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMaxUnits() + 1);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void probationaryStudentBelowMinimumUnits() throws IneligibleStudentException, ScheduleConflictException,
			SectionFullException, SubjectUnitsRestrictionException, UnsatisfiedPrerequisiteException {
		StudentStatus studentStatus = StudentStatus.PROBATIONARY;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMinUnits() - MAX_UNITS);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void graduatingStudentEnrollmentFormWithNoUnits() throws IneligibleStudentException,
			ScheduleConflictException, SectionFullException, SubjectUnitsRestrictionException,
			UnsatisfiedPrerequisiteException {
		StudentStatus studentStatus = StudentStatus.GRADUATING;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, 0);
	}

	@Test(expected = SectionFullException.class)
	public void classCardsMustNotExceedMaxCapacity() throws SectionFullException, IneligibleStudentException,
			SubjectUnitsRestrictionException, ScheduleConflictException, UnsatisfiedPrerequisiteException {
		Section section = getFirstSubjectSection();
		for (int i = 1; i <= Section.MAX_STUDENTS + 1; i++) {
			Student student = new Student(i, StudentStatus.GRADUATING, curriculum);
			student.getEnrollmentFormBuilder().addSection(section).enroll();
		}
	}

	@Test(expected = ScheduleConflictException.class)
	public void studentWithScheduleConflict() throws SectionFullException, IneligibleStudentException,
			SubjectUnitsRestrictionException, ScheduleConflictException, UnsatisfiedPrerequisiteException {
		Student student = new Student(1, StudentStatus.GRADUATING, curriculum);

		Schedule schedule = new Schedule(academicTerm, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);

		Subject subject1 = curriculum.getSubjects().get(0);
		Teacher teacher1 = new Teacher(1, "Juan Nakpil");
		Section section1 = new Section("S19", subject1, schedule, teacher1);

		Subject subject2 = curriculum.getSubjects().get(1);
		Teacher teacher2 = new Teacher(1, "Carlos Garcia");
		Section section2 = new Section("S20", subject2, schedule, teacher2);

		EnrollmentFormBuilder enrollmentFormBuilder = student.getEnrollmentFormBuilder();
		enrollmentFormBuilder.addSection(section1);
		enrollmentFormBuilder.addSection(section2);

		enrollmentFormBuilder.enroll();
	}

	@Test(expected = ScheduleConflictException.class)
	public void studentEnrollingSameSection() throws SectionFullException, IneligibleStudentException,
			SubjectUnitsRestrictionException, ScheduleConflictException, UnsatisfiedPrerequisiteException {
		Student student = new Student(1, StudentStatus.GRADUATING, curriculum);

		Schedule schedule = new Schedule(academicTerm, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);
		Subject subject1 = curriculum.getSubjects().get(0);
		Teacher teacher1 = new Teacher(1, "Juan Nakpil");
		Section section1 = new Section("S19", subject1, schedule, teacher1);

		EnrollmentFormBuilder enrollmentFormBuilder = student.getEnrollmentFormBuilder();
		enrollmentFormBuilder.addSection(section1);
		enrollmentFormBuilder.addSection(section1);

		enrollmentFormBuilder.enroll();
	}
	
		
	@Test
	public void passingAverage() throws Exception{
		Student newStudent = new Student(1, StudentStatus.NEW, curriculum);
		List <Section> sections = getSixSubjectsNoPrerequisites();
		EnrollmentForm ef = newStudent.getEnrollmentFormBuilder().addSection(sections.get(0))
											 					 .addSection(sections.get(1))
																 .addSection(sections.get(2))
																 .addSection(sections.get(3))
																 .addSection(sections.get(4))
																 .addSection(sections.get(5))
																 .enroll();
		List<ClassCard> classCards = ef.getClassCards();
		classCards.get(0).setGrade(new BigDecimal("1.00"));
		classCards.get(1).setGrade(new BigDecimal("1.00"));
		classCards.get(2).setGrade(new BigDecimal("3.00"));
		classCards.get(3).setGrade(new BigDecimal("3.00"));
		classCards.get(4).setGrade(new BigDecimal("5.00"));
		classCards.get(5).setGrade(new BigDecimal("5.00"));
		
		assertEquals(0, new BigDecimal("3.00").compareTo(newStudent.calculateAverage()));
	}
	
	private List<Section> getSixSubjectsNoPrerequisites() throws Exception{
		List<Section> sectionList = new ArrayList<Section>();
		List<Subject> subjectList = curriculum.getSubjects();
		sectionList.add(createSection("A", subjectList.get(0), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000));
		sectionList.add(createSection("A", subjectList.get(1), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1000_TO_1130));
		sectionList.add(createSection("A", subjectList.get(2), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1130_TO_1300));
		sectionList.add(createSection("A", subjectList.get(3), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1300_TO_1430));
		sectionList.add(createSection("A", subjectList.get(4), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1430_TO_1600));
		sectionList.add(createSection("A", subjectList.get(5), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1600_TO_1730));
		return sectionList;
	}

	private void enrollUnits(Student student, int unitsToTake) throws IneligibleStudentException,
			ScheduleConflictException, SectionFullException, SubjectUnitsRestrictionException,
			UnsatisfiedPrerequisiteException {
		EnrollmentFormBuilder enrollmentFormBuilder = student.getEnrollmentFormBuilder();
		addUnitsToEnrollmentForm(unitsToTake, enrollmentFormBuilder);
		enrollmentFormBuilder.enroll();
	}

	private void addUnitsToEnrollmentForm(int unitsToTake, EnrollmentFormBuilder enrollmentFormBuilder)
			throws ScheduleConflictException, SectionFullException, SubjectUnitsRestrictionException,
			UnsatisfiedPrerequisiteException {
		Iterator<Subject> subjectsIterator = curriculum.getSubjects().iterator();
		ScheduleDays[] scheduleDaysList = ScheduleDays.values();
		ScheduleTimes[] scheduleTimesList = ScheduleTimes.values();

		int scheduleDaysIndex = 0;
		int scheduleTimesIndex = 0;
		int totalUnits = 0;

		while (totalUnits < unitsToTake) {
			Subject subject = subjectsIterator.next();

			if (subject.hasPrerequisites()) {
				continue;
			}

			totalUnits += subject.getNumberOfUnits();

			ScheduleDays scheduleDays = scheduleDaysList[scheduleDaysIndex];
			ScheduleTimes scheduleTimes = scheduleTimesList[scheduleTimesIndex];

			String sectionName = "A_" + scheduleDaysIndex + "_" + scheduleTimesIndex;
			Section section = createSection(sectionName, subject, scheduleDays, scheduleTimes);

			enrollmentFormBuilder.addSection(section);

			scheduleTimesIndex++;
			if (scheduleTimesIndex == scheduleTimesList.length) {
				scheduleTimesIndex = 0;
				scheduleDaysIndex++;
			}
		}
	}

	
}