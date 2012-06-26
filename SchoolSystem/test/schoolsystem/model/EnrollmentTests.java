package schoolsystem.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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
		curriculum = Curriculum.BS_COMPUTER_SCIENCE;
	}

	private Section getFirstSubjectSection() throws ScheduleConflictException {
		Subject subject = curriculum.getSubjects().get(0);
		return createSection(1, "A", subject, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);
	}

	private static Section createSection(int sectionId, String sectionName, Subject subject, ScheduleDays scheduleDays,
			ScheduleTimes scheduleTimes) throws ScheduleConflictException {
		AcademicTerm academicTerm = AcademicTerm.academicTermAfterCurrent();
		Schedule schedule = new Schedule(academicTerm, scheduleDays, scheduleTimes);
		Teacher teacher = new Teacher(1, "John Doe");
		return new Section(sectionId, sectionName, subject, schedule, teacher);
	}

	@Test
	public void enrollEligibleStudentInSectionWithNoPrereq() throws SectionFullException, IneligibleStudentException,
			SubjectUnitsRestrictionException, ScheduleConflictException, UnsatisfiedPrerequisiteException {
		Student student = new Student(1, StudentStatus.GRADUATING, curriculum);
		Section section = getFirstSubjectSection();
		
		EnrollmentForm ef = student.getEnrollmentForm();
		ef.addSection(section);
		ef.submitForEnrollment();
		
		assertEquals(1, student.getNumEnrollmentForms());
	}

	@Test(expected = UnsatisfiedPrerequisiteException.class)
	public void enrollNewStudentsWithUnsatisfiedPrereq() throws Exception {
		Student student = new Student(1, StudentStatus.NEW, curriculum);
		enrollStudentWithPrereq(student);
	}

	@Test(expected = UnsatisfiedPrerequisiteException.class)
	public void enrollContinuingStudentsWithUnsatisfiedPrereq() throws Exception {
		Student student = new Student(1, StudentStatus.CONTINUING, curriculum);
		enrollStudentWithPrereq(student);
	}

	@Test(expected = UnsatisfiedPrerequisiteException.class)
	public void enrollProbationaryStudentsWithUnsatisfiedPrereq() throws Exception {
		Student student = new Student(1, StudentStatus.PROBATIONARY, curriculum);
		enrollStudentWithPrereq(student);
	}

	@Test(expected = IllegalStateException.class)
	public void enrollStudentWhenPreviousTermHasNoGrades() throws Exception {
		Student student = new Student(1, StudentStatus.CONTINUING, curriculum);

		enrollTerm1WithNoPrereq(student);
		enrollTerm2WithPrereq(student);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void enrollingPassedSubjectsTwice() throws Exception {
		Student student = new Student(1, StudentStatus.CONTINUING, curriculum);

		enrollTerm1WithNoPrereq(student);
		for (ClassCard term1ClassCard : student.getEnrollmentForms().get(0).getClassCards()) {
			term1ClassCard.setGrade(Grade.G1_75);
		}

		Subject subject = getBSCSSubject("HI 16");
		Section section = createSection(5, "G", subject, ScheduleDays.WED_AND_SAT, ScheduleTimes.FROM_1130_TO_1300);

		EnrollmentForm enrollmentForm = student.getEnrollmentForm();
		enrollmentForm.addSection(section);
	}

	@Test
	public void addingPreviouslyFailedSubject() throws Exception {
		Student student = new Student(1, StudentStatus.CONTINUING, curriculum);

		enrollTerm1WithNoPrereq(student);
		for (ClassCard term1ClassCard : student.getEnrollmentForms().get(0).getClassCards()) {
			term1ClassCard.setGrade(Grade.G5_00);
		}

		Subject subject = getBSCSSubject("HI 16");
		Section section = createSection(5, "G", subject, ScheduleDays.WED_AND_SAT, ScheduleTimes.FROM_1130_TO_1300);

		EnrollmentForm enrollmentForm = student.getEnrollmentForm();
		enrollmentForm.addSection(section);
	}

	@Test
	public void enrollStudentWithPrereq() throws Exception {
		Student student = new Student(1, StudentStatus.PROBATIONARY, curriculum);

		enrollTerm1WithNoPrereq(student);

		for (ClassCard term1ClassCard : student.getEnrollmentForms().get(0).getClassCards()) {
			term1ClassCard.setGrade(Grade.G1_75);
		}

		enrollTerm2WithPrereq(student);

		assertEquals(2, student.getNumEnrollmentForms());
	}

	private void enrollTerm2WithPrereq(Student student) throws Exception {
		StudentStatus status = student.getStatus();
		EnrollmentForm term2EnrollmentForm = student.getEnrollmentForm();
		addUnitsToEnrollmentForm(curriculum, 7, status.getMinUnits(), term2EnrollmentForm);

		Subject subjectWithPrereq = getBSCSSubject("HI 166");
		Section sectionWithPrereq = createSection(5, "AV", subjectWithPrereq, ScheduleDays.WED_AND_SAT,
				ScheduleTimes.FROM_1600_TO_1730);
		term2EnrollmentForm.addSection(sectionWithPrereq);

		term2EnrollmentForm.submitForEnrollment();
	}

	private void enrollTerm1WithNoPrereq(Student student) throws Exception {
		StudentStatus status = student.getStatus();

		EnrollmentForm term1EnrollmentForm = student.getEnrollmentForm();
		addUnitsToEnrollmentForm(curriculum, status.getMinUnits(), term1EnrollmentForm);

		Subject prereqSubject = getBSCSSubject("HI 16");
		Section prereqSection = createSection(5, "AV", prereqSubject, ScheduleDays.WED_AND_SAT,
				ScheduleTimes.FROM_1000_TO_1130);
		term1EnrollmentForm.addSection(prereqSection);

		term1EnrollmentForm.submitForEnrollment();
	}

	private Subject getBSCSSubject(String subjectName) {
		List<Subject> subjects = Curriculum.BS_COMPUTER_SCIENCE.findSubject(subjectName);
		assertTrue(subjects.size() == 1);
		return subjects.get(0);
	}

	private void enrollStudentWithPrereq(Student student) throws Exception {
		Subject subjectWithPrereq = getSubjectWithPrereq();

		Section sectionWithPrereq = createSection(5, "C", subjectWithPrereq, ScheduleDays.WED_AND_SAT,
				ScheduleTimes.FROM_1600_TO_1730);

		EnrollmentForm enrollmentForm = student.getEnrollmentForm();

		StudentStatus studentStatus = student.getStatus();
		addUnitsToEnrollmentForm(curriculum, studentStatus.getMinUnits(), enrollmentForm);

		enrollmentForm.addSection(sectionWithPrereq);
		enrollmentForm.submitForEnrollment();
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
		ineligibleStudent.getEnrollmentForm();
	}

	@Test(expected = IneligibleStudentException.class)
	public void enrollGraduatedStudent() throws SectionFullException, IneligibleStudentException {
		Student graduatedStudent = new Student(2, StudentStatus.GRADUATE, Curriculum.BS_COMPUTER_SCIENCE);
		graduatedStudent.getEnrollmentForm();
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void newStudentBelowMinimumUnits() throws Exception {
		StudentStatus studentStatus = StudentStatus.NEW;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMinUnits() - MAX_UNITS);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void newStudentExceededMaximumUnits() throws Exception {
		StudentStatus studentStatus = StudentStatus.NEW;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMaxUnits() + 1);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void continuingStudentExceededMaximumUnits() throws Exception {
		StudentStatus studentStatus = StudentStatus.CONTINUING;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMaxUnits() + 1);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void continuingStudentBelowMinimumUnits() throws Exception {
		StudentStatus studentStatus = StudentStatus.CONTINUING;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMinUnits() - MAX_UNITS);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void probationaryStudentExceededMaximumUnits() throws Exception {
		StudentStatus studentStatus = StudentStatus.CONTINUING;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMaxUnits() + 1);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void probationaryStudentBelowMinimumUnits() throws Exception {
		StudentStatus studentStatus = StudentStatus.PROBATIONARY;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMinUnits() - MAX_UNITS);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void graduatingStudentEnrollmentFormWithNoUnits() throws Exception {
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
			EnrollmentForm enrollmentForm = student.getEnrollmentForm();
			enrollmentForm.addSection(section);
			enrollmentForm.submitForEnrollment();
		}
	}

	@Test(expected = ScheduleConflictException.class)
	public void studentWithScheduleConflict() throws Exception {
		Student student = new Student(1, StudentStatus.GRADUATING, curriculum);

		Schedule schedule = new Schedule(academicTerm, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);

		Subject subject1 = curriculum.getSubjects().get(0);
		Teacher teacher1 = new Teacher(1, "Juan Nakpil");
		Section section1 = new Section(1, "S19", subject1, schedule, teacher1);

		Subject subject2 = curriculum.getSubjects().get(1);
		Teacher teacher2 = new Teacher(1, "Carlos Garcia");
		Section section2 = new Section(2, "S20", subject2, schedule, teacher2);

		EnrollmentForm enrollmentForm = student.getEnrollmentForm();
		enrollmentForm.addSection(section1);
		enrollmentForm.addSection(section2);

		enrollmentForm.submitForEnrollment();
	}

	@Test(expected = ScheduleConflictException.class)
	public void studentEnrollingSameSection() throws Exception {
		Student student = new Student(1, StudentStatus.GRADUATING, curriculum);

		Schedule schedule = new Schedule(academicTerm, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);
		Subject subject1 = curriculum.getSubjects().get(0);
		Teacher teacher1 = new Teacher(1, "Juan Nakpil");
		Section section1 = new Section(1, "S19", subject1, schedule, teacher1);

		EnrollmentForm enrollmentForm = student.getEnrollmentForm();
		enrollmentForm.addSection(section1);
		enrollmentForm.addSection(section1);

		enrollmentForm.submitForEnrollment();
	}

	@Test
	public void passingAverage() throws Exception {
		Student newStudent = new Student(1, StudentStatus.NEW, curriculum);
		List<Section> sections = getSixSectionsNoPrerequisites();

		EnrollmentForm ef = newStudent.getEnrollmentForm();
		ef.addSection(sections.get(0));
		ef.addSection(sections.get(1));
		ef.addSection(sections.get(2));
		ef.addSection(sections.get(3));
		ef.addSection(sections.get(4));
		ef.addSection(sections.get(5));
		ef.submitForEnrollment();

		List<ClassCard> classCards = ef.getClassCards();
		classCards.get(0).setGrade(Grade.G1_00);
		classCards.get(1).setGrade(Grade.G1_00);
		classCards.get(2).setGrade(Grade.G3_00);
		classCards.get(3).setGrade(Grade.G3_00);
		classCards.get(4).setGrade(Grade.G5_00);
		classCards.get(5).setGrade(Grade.G5_00);

		assertEquals(0, new BigDecimal("3.00").compareTo(newStudent.calculateAverage()));
	}

	private List<Section> getSixSectionsNoPrerequisites() throws Exception {
		List<Section> sectionList = new ArrayList<Section>();
		List<Subject> subjectList = curriculum.getSubjects();

		Subject subject = subjectList.get(0);
		assertTrue(!subject.hasPrerequisites());
		sectionList.add(createSection(1, "A", subject, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000));

		subject = subjectList.get(1);
		assertTrue(!subject.hasPrerequisites());
		sectionList.add(createSection(2, "A", subject, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1000_TO_1130));

		subject = subjectList.get(2);
		assertTrue(!subject.hasPrerequisites());
		sectionList.add(createSection(3, "A", subject, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1130_TO_1300));

		subject = subjectList.get(3);
		assertTrue(!subject.hasPrerequisites());
		sectionList.add(createSection(4, "A", subject, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1300_TO_1430));

		subject = subjectList.get(4);
		assertTrue(!subject.hasPrerequisites());
		sectionList.add(createSection(5, "A", subject, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1430_TO_1600));

		subject = subjectList.get(5);
		assertTrue(!subject.hasPrerequisites());
		sectionList.add(createSection(6, "A", subject, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1600_TO_1730));

		return sectionList;
	}

	private void enrollUnits(Student student, int subjectStartIndex, int unitsToTake) throws Exception {
		EnrollmentForm enrollmentFormBuilder = student.getEnrollmentForm();
		addUnitsToEnrollmentForm(curriculum, subjectStartIndex, unitsToTake, enrollmentFormBuilder);
		enrollmentFormBuilder.submitForEnrollment();
	}

	private void enrollUnits(Student student, int unitsToTake) throws Exception {
		enrollUnits(student, 0, unitsToTake);
	}

	static void addUnitsToEnrollmentForm(Curriculum curriculum, int unitsToTake, EnrollmentForm enrollmentForm)
			throws Exception {
		addUnitsToEnrollmentForm(curriculum, 0, unitsToTake, enrollmentForm);
	}

	static void addUnitsToEnrollmentForm(Curriculum curriculum, int subjectStartIndex, int unitsToTake,
			EnrollmentForm enrollmentForm) throws Exception {

		Iterator<Subject> subjectsIterator = curriculum.getSubjects().iterator();
		ScheduleDays[] scheduleDaysList = ScheduleDays.values();
		ScheduleTimes[] scheduleTimesList = ScheduleTimes.values();

		int scheduleDaysIndex = 0;
		int scheduleTimesIndex = 0;
		int totalUnits = 0;
		int subjectIndex = 0;

		while (totalUnits < unitsToTake) {
			Subject subject = subjectsIterator.next();

			if (subject.hasPrerequisites()) {
				continue;
			}

			ScheduleDays scheduleDays = scheduleDaysList[scheduleDaysIndex];
			ScheduleTimes scheduleTimes = scheduleTimesList[scheduleTimesIndex];

			scheduleTimesIndex++;
			if (scheduleTimesIndex == scheduleTimesList.length) {
				scheduleTimesIndex = 0;
				scheduleDaysIndex++;
			}

			if (subjectStartIndex > subjectIndex) {
				subjectIndex++;
				continue;
			} else {
				subjectIndex++;
			}

			totalUnits += subject.getNumberOfUnits();

			String sectionName = "A_" + scheduleDaysIndex + "_" + scheduleTimesIndex;
			Section section = createSection(subjectIndex + 200, sectionName, subject, scheduleDays, scheduleTimes);

			enrollmentForm.addSection(section);
		}
	}

}
