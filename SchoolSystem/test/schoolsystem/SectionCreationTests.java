package schoolsystem;

import org.junit.Test;

import schoolsystem.model.Curriculum;
import schoolsystem.model.Section;
import schoolsystem.model.Subject;
import schoolsystem.model.Teacher;
import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;
import schoolsystem.model.schedule.ScheduleDays;
import schoolsystem.model.schedule.ScheduleTimes;

public class SectionCreationTests {

	@Test
	public void normalAdding() {
		Subject subject = createSubject();
		Schedule schedule = new Schedule(ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);
		Teacher teacher = createTeacher();
		Section.createSection("S19", subject, schedule, teacher);
	}

//	@Test(expected = ScheduleConflictException.class)
//	public void teacherHandlesSameSchedule() {
//		Teacher teacher = createTeacher();
//		Schedule schedule = new Schedule(ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);
//		
//		Subject subject1 = createSubject();
//		Section.createSection("S19", subject1, schedule, teacher);
//		
//		Subject subject2 = createSubject();
//		Section.createSection("S20", subject2, schedule, teacher);
//	}

	private Teacher createTeacher() {
		Teacher teacher = new Teacher(1, createRandomStr("Teacher"));
		return teacher;
	}

	private Subject createSubject() {
		Subject subject = new Subject(createRandomStr("Subject"));
		return subject;
	}

	private String createRandomStr(String prefix) {
		return prefix+" " + Math.random();
	}
}
