package schoolsystem;

import static org.junit.Assert.*;

import org.junit.Test;

import schoolsystem.model.Section;
import schoolsystem.model.SectionNameConflictException;
import schoolsystem.model.Subject;
import schoolsystem.model.Teacher;
import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;
import schoolsystem.model.schedule.ScheduleDays;
import schoolsystem.model.schedule.ScheduleTimes;

public class SectionCreationTests {

	@Test
	public void teacherHasNoScheduleConflict() throws ScheduleConflictException, SectionNameConflictException {
		Teacher teacher = createTeacher();

		Schedule schedule1 = new Schedule(ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);
		Subject subject1 = createSubject();
		String sectionName1 = "S19";
		Section section1 = subject1.createSection(sectionName1, schedule1, teacher);
		
		checkSection(section1, teacher, schedule1, subject1, sectionName1);
		
		Schedule schedule2 = new Schedule(ScheduleDays.TUE_AND_FRI, ScheduleTimes.FROM_0830_TO_1000);
		Subject subject2 = createSubject();
		String sectionName2 = "S20";
		Section section2 = subject2.createSection(sectionName2, schedule2, teacher);
		
		checkSection(section2, teacher, schedule2, subject2, sectionName2);
	}

	private void checkSection(Section section, Teacher teacher, Schedule schedule, Subject subject, String sectionName) {
		assertEquals(section.getSchedule(), schedule);
		assertEquals(section.getSectionName(), sectionName);
		assertEquals(section.getTeacher(), teacher);
		assertEquals(section.getSubject(), subject);
	}

	@Test(expected = ScheduleConflictException.class)
	public void teacherHasScheduleConflict() throws ScheduleConflictException, SectionNameConflictException {
		Teacher teacher = createTeacher();
		Schedule schedule = new Schedule(ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);

		Subject subject1 = createSubject();
		subject1.createSection("S19", schedule, teacher);

		Subject subject2 = createSubject();
		subject2.createSection("S20", schedule, teacher);
	}

	@Test(expected = SectionNameConflictException.class)
	public void subjectSectionDuplicateNames() throws SectionNameConflictException, ScheduleConflictException {
		Teacher teacher = createTeacher();
		Subject subject = createSubject();

		Schedule schedule1 = new Schedule(ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);
		subject.createSection("S19", schedule1, teacher);

		Schedule schedule2 = new Schedule(ScheduleDays.TUE_AND_FRI, ScheduleTimes.FROM_0830_TO_1000);
		subject.createSection("S19", schedule2, teacher);
	}

	private Teacher createTeacher() {
		Teacher teacher = new Teacher(1, createRandomStr("Teacher"));
		return teacher;
	}

	private Subject createSubject() {
		Subject subject = new Subject(createRandomStr("Subject"));
		return subject;
	}

	private String createRandomStr(String prefix) {
		return prefix + " " + Math.random();
	}
}
