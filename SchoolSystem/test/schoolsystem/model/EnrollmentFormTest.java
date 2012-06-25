package schoolsystem.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import schoolsystem.model.schedule.AcademicTerm;
import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;
import schoolsystem.model.schedule.ScheduleDays;
import schoolsystem.model.schedule.ScheduleTimes;

public class EnrollmentFormTest {

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
	public void hasSection() throws SectionFullException, SubjectUnitsRestrictionException, ScheduleConflictException,
			IneligibleStudentException {
		Student student = new Student(1, StudentStatus.GRADUATING, curriculum);
		Section section = getFirstSubjectSection();
		EnrollmentForm enrollmentForm = student.getEnrollmentFormBuilder().addSection(section).enroll();

		assertTrue(enrollmentForm.hasSection(section));

		Subject subjectNotInEnrollmentForm = curriculum.getSubjects().get(1);
		Section sectionNotInEnrollmentForm = createSection("B", subjectNotInEnrollmentForm, ScheduleDays.MON_AND_THU,
				ScheduleTimes.FROM_1300_TO_1430);

		assertFalse(enrollmentForm.hasSection(sectionNotInEnrollmentForm));
	}
}
