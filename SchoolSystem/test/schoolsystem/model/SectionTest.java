package schoolsystem.model;

import static org.junit.Assert.assertEquals;

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
		academicTerm = AcademicTerm.academicTermAfterCurrent();
		curriculum = Curriculum.BS_COMPUTER_SCIENCE;
	}

	@Test(expected = ScheduleConflictException.class)
	public void teacherHasScheduleConflict() throws ScheduleConflictException {
		Teacher teacher = createTeacher();
		Schedule schedule = new Schedule(academicTerm, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);

		Subject subject1 = createSubject();
		new Section(1, "S19", subject1, schedule, teacher);

		Subject subject2 = createSubject();
		new Section(2, "S20", subject2, schedule, teacher);
	}
	
	@Test
	public void teacherHasNoScheduleConflict() throws ScheduleConflictException {
		Teacher teacher = createTeacher();

		Schedule schedule1 = new Schedule(academicTerm, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);
		Subject subject1 = createSubject();
		String sectionName1 = "S19";
		Section section1 = new Section(1, sectionName1, subject1, schedule1, teacher);

		checkSection(section1, teacher, schedule1, subject1, sectionName1);

		Schedule schedule2 = new Schedule(academicTerm, ScheduleDays.TUE_AND_FRI, ScheduleTimes.FROM_0830_TO_1000);
		Subject subject2 = createSubject();
		String sectionName2 = "S20";
		Section section2 = new Section(2, sectionName2, subject2, schedule2, teacher);

		checkSection(section2, teacher, schedule2, subject2, sectionName2);
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
