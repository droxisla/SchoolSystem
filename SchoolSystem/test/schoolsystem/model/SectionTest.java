package schoolsystem.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import schoolsystem.model.schedule.AcademicTerm;
import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;
import schoolsystem.model.schedule.ScheduleDays;
import schoolsystem.model.schedule.ScheduleTimes;

public class SectionTest {

	private AcademicTerm academicTerm;
	private Curriculum curriculum;

	@Before
	public void createFixture() {
		SectionManager.getInstance().reset();
		academicTerm = AcademicTerm.academicTermAfterCurrent();
		curriculum = Curriculum.BS_COMPUTER_SCIENCE;
	}

	@Test
	public void teacherHasNoScheduleConflict() throws ScheduleConflictException {
		Teacher teacher = createTeacher();

		Schedule schedule1 = new Schedule(academicTerm, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);
		Subject subject1 = createSubject();
		String sectionName1 = "S19";
		Section section1 = new Section(sectionName1, subject1, schedule1, teacher);

		checkSection(section1, teacher, schedule1, subject1, sectionName1);

		Schedule schedule2 = new Schedule(academicTerm, ScheduleDays.TUE_AND_FRI, ScheduleTimes.FROM_0830_TO_1000);
		Subject subject2 = createSubject();
		String sectionName2 = "S20";
		Section section2 = new Section(sectionName2, subject2, schedule2, teacher);

		checkSection(section2, teacher, schedule2, subject2, sectionName2);
	}

	@Test(expected = ScheduleConflictException.class)
	public void teacherHasScheduleConflict() throws ScheduleConflictException {
		Teacher teacher = createTeacher();
		Schedule schedule = new Schedule(academicTerm, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);

		Subject subject1 = createSubject();
		new Section("S19", subject1, schedule, teacher);

		Subject subject2 = createSubject();
		new Section("S20", subject2, schedule, teacher);
	}

	@Test(expected = SectionFullException.class)
	public void classCardsNotLost() throws SectionFullException, IneligibleStudentException,
			SubjectUnitsRestrictionException, ScheduleConflictException {
		Teacher teacher = createTeacher();
		Subject subject = createSubject();
		Schedule schedule = new Schedule(academicTerm, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);

		for (int i = 1; i <= Section.MAX_STUDENTS + 1; i++) {
			// Creating the same section still uses the same list of class cards
			Section section = new Section("A", subject, schedule, teacher);

			Student student = new Student(i, StudentStatus.GRADUATING, curriculum);
			student.getEnrollmentFormBuilder().addSection(section).enroll();
		}
	}

	@Test
	public void sectionFull() throws SectionFullException, IneligibleStudentException,
			SubjectUnitsRestrictionException, ScheduleConflictException {
		Teacher teacher = createTeacher();
		Subject subject = createSubject();
		Schedule schedule = new Schedule(academicTerm, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);
		Section section = new Section("A", subject, schedule, teacher);

		for (int i = 1; i <= Section.MAX_STUDENTS; i++) {
			Student student = new Student(i, StudentStatus.GRADUATING, curriculum);
			student.getEnrollmentFormBuilder().addSection(section).enroll();

			if (i == Section.MAX_STUDENTS) {
				assertTrue(section.isFull());
			} else {
				assertFalse(section.isFull());
			}
		}
	}

	private void checkSection(Section section, Teacher teacher, Schedule schedule, Subject subject, String sectionName) {
		assertEquals(section.getSchedule(), schedule);
		assertEquals(section.getSectionName(), sectionName);
		assertEquals(section.getTeacher(), teacher);
		assertEquals(section.getSubject(), subject);
	}

	private Teacher createTeacher() {
		return new Teacher(1, "John Smith");
	}

	private Subject createSubject() {
		return curriculum.getSubjects().get(0);
	}
}
