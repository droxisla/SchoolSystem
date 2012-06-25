package schoolsystem.model;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

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

	private Section createSection(String sectionName, Subject subject, ScheduleDays scheduleDays, ScheduleTimes scheduleTimes) throws ScheduleConflictException {
		Schedule schedule = new Schedule(academicTerm, scheduleDays, scheduleTimes);
		Teacher teacher = new Teacher(1, "John Doe");
		return new Section(sectionName, subject, schedule, teacher);
	}

	@Test
	public void enrollEligibleStudentInSectionWithNoPrereq() throws SectionFullException, IneligibleStudentException, SubjectUnitsRestrictionException, ScheduleConflictException {
		Student student = new Student(1, StudentStatus.GRADUATING, curriculum);
		Section section = getFirstSubjectSection();
		student.getEnrollmentFormBuilder().addSection(section).enroll();
		assertEquals(1, student.getNumEnrollmentForms());
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
	public void newStudentBelowMinimumUnits() throws IneligibleStudentException, ScheduleConflictException, SectionFullException, SubjectUnitsRestrictionException {
		StudentStatus studentStatus = StudentStatus.NEW;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMinUnits() - MAX_UNITS);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void newStudentExceededMaximumUnits() throws IneligibleStudentException, ScheduleConflictException, SectionFullException, SubjectUnitsRestrictionException {
		StudentStatus studentStatus = StudentStatus.NEW;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMaxUnits() + 1);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void continuingStudentExceededMaximumUnits() throws IneligibleStudentException, ScheduleConflictException, SectionFullException, SubjectUnitsRestrictionException {
		StudentStatus studentStatus = StudentStatus.CONTINUING;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMaxUnits() + 1);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void continuingStudentBelowMinimumUnits() throws IneligibleStudentException, ScheduleConflictException, SectionFullException, SubjectUnitsRestrictionException {
		StudentStatus studentStatus = StudentStatus.CONTINUING;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMinUnits() - MAX_UNITS);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void probationaryStudentExceededMaximumUnits() throws IneligibleStudentException, ScheduleConflictException, SectionFullException, SubjectUnitsRestrictionException {
		StudentStatus studentStatus = StudentStatus.CONTINUING;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMaxUnits() + 1);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void probationaryStudentBelowMinimumUnits() throws IneligibleStudentException, ScheduleConflictException, SectionFullException, SubjectUnitsRestrictionException {
		StudentStatus studentStatus = StudentStatus.PROBATIONARY;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, studentStatus.getMinUnits() - MAX_UNITS);
	}

	@Test(expected = SubjectUnitsRestrictionException.class)
	public void graduatingStudentEnrollmentFormWithNoUnits() throws IneligibleStudentException, ScheduleConflictException, SectionFullException, SubjectUnitsRestrictionException {
		StudentStatus studentStatus = StudentStatus.GRADUATING;
		Student student = new Student(2, studentStatus, curriculum);
		enrollUnits(student, 0);
	}

	@Test(expected = SectionFullException.class)
	public void classCardsMustNotExceedMaxCapacity() throws SectionFullException, IneligibleStudentException, SubjectUnitsRestrictionException, ScheduleConflictException {
		Section section = getFirstSubjectSection();
		for (int i = 1; i <= Section.MAX_STUDENTS + 1; i++) {
			Student student = new Student(i, StudentStatus.GRADUATING, curriculum);
			student.getEnrollmentFormBuilder().addSection(section).enroll();
		}
	}

	@Test(expected = ScheduleConflictException.class)
	public void studentWithScheduleConflict() throws SectionFullException, IneligibleStudentException, SubjectUnitsRestrictionException, ScheduleConflictException {
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
	public void studentEnrollingSameSection() throws SectionFullException, IneligibleStudentException, SubjectUnitsRestrictionException, ScheduleConflictException {
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
	
	private void enrollUnits(Student student, int unitsToTake) throws IneligibleStudentException, ScheduleConflictException, SectionFullException, SubjectUnitsRestrictionException {
		EnrollmentFormBuilder enrollmentFormBuilder = student.getEnrollmentFormBuilder();

		Iterator<Subject> subjectsIterator = curriculum.getSubjects().iterator();
		ScheduleDays[] scheduleDaysList = ScheduleDays.values();
		ScheduleTimes[] scheduleTimesList = ScheduleTimes.values();

		int scheduleDaysIndex = 0;
		int scheduleTimesIndex = 0;
		int totalUnits = 0;

		while (totalUnits < unitsToTake) {
			Subject subject = subjectsIterator.next();

			if (!subject.getPrerequisites().isEmpty()) {
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

		enrollmentFormBuilder.enroll();
	}
}
