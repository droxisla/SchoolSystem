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
		curriculum = Curriculum.BS_COMPUTER_SCIENCE;
	}

	private Section getFirstSubjectSection() throws ScheduleConflictException {
		Subject subject = curriculum.getSubjects().get(0);
		return createSection(1, "A", subject, ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);
	}

	private Section createSection(int sectionId, String sectionName, Subject subject, ScheduleDays scheduleDays,
			ScheduleTimes scheduleTimes) throws ScheduleConflictException {
		Schedule schedule = new Schedule(academicTerm, scheduleDays, scheduleTimes);
		Teacher teacher = new Teacher(1, "John Doe");
		return new Section(sectionId, sectionName, subject, schedule, teacher);
	}

	@Test
	public void hasSection() throws SectionFullException, SubjectUnitsRestrictionException, ScheduleConflictException,
			IneligibleStudentException, UnsatisfiedPrerequisiteException {
		Student student = new Student(1, "Juan dela Cruz", StudentStatus.GRADUATING, curriculum);
		Section section = getFirstSubjectSection();
		
		EnrollmentForm enrollmentForm = student.getNewEnrollmentForm();
		enrollmentForm.addSection(section);
		enrollmentForm.submitForEnrollment();

		assertTrue(enrollmentForm.hasSection(section));

		Subject subjectNotInEnrollmentForm = curriculum.getSubjects().get(1);
		Section sectionNotInEnrollmentForm = createSection(2, "B", subjectNotInEnrollmentForm,
				ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1300_TO_1430);

		assertFalse(enrollmentForm.hasSection(sectionNotInEnrollmentForm));
	}
}
