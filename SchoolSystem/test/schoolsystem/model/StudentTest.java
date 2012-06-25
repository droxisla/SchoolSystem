package schoolsystem.model;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import schoolsystem.model.EnrollmentForm.EnrollmentFormBuilder;
import schoolsystem.model.schedule.ScheduleConflictException;

public class StudentTest {

	private Curriculum curriculum;

	@Before
	public void createFixture() throws Exception {
		SectionManager.getInstance().reset();
		curriculum = Curriculum.BS_COMPUTER_SCIENCE;
	}

	@Test
	public void hasPassedSubject() throws ScheduleConflictException, SectionFullException, SubjectUnitsRestrictionException, UnsatisfiedPrerequisiteException, IneligibleStudentException {
		Student student = new Student(1, StudentStatus.CONTINUING, curriculum);
		EnrollmentFormBuilder enrollmentFormBuilder = student.getEnrollmentFormBuilder();
		EnrollmentTests.addUnitsToEnrollmentForm(curriculum, StudentStatus.CONTINUING.getMinUnits(),
				enrollmentFormBuilder);

		EnrollmentForm enrollmentForm = enrollmentFormBuilder.enroll();
		
		rateClassCards(enrollmentForm.getClassCards(), "0");
	}

	private void rateClassCards(List<ClassCard> classCards, String score) {
		for(ClassCard classCard:classCards) {
			classCard.setGrade(new BigDecimal(score));
		}
	}
}
