package schoolsystem.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StudentTest {

	private Curriculum curriculum;

	@Before
	public void createFixture() throws Exception {
		curriculum = Curriculum.BS_COMPUTER_SCIENCE;
	}

	@Test
	public void hasPassedSubject() throws Exception {
		Student student = new Student(1, "Juan dela Cruz", StudentStatus.CONTINUING, curriculum);
		EnrollmentForm enrollmentForm = buildEnrollmentFormThenGradeWith(student, Grade.G3_00);

		List<Subject> subjects = getSubjects(enrollmentForm);

		for (Subject subject : subjects) {
			assertTrue(student.hasPassedSubject(subject));
		}
	}

	@Test
	public void hasFailedSubject() throws Exception {
		Student student = new Student(1, "Juan dela Cruz", StudentStatus.CONTINUING, curriculum);
		EnrollmentForm enrollmentForm = buildEnrollmentFormThenGradeWith(student, Grade.G5_00);

		List<Subject> subjects = getSubjects(enrollmentForm);

		for (Subject subject : subjects) {
			assertFalse(student.hasPassedSubject(subject));
		}
	}

	private EnrollmentForm buildEnrollmentFormThenGradeWith(Student student, Grade grade) throws Exception {
		EnrollmentForm enrollmentForm = student.getNewEnrollmentForm();
		EnrollmentTests.addUnitsToEnrollmentForm(curriculum, StudentStatus.CONTINUING.getMinUnits(), enrollmentForm);

		enrollmentForm.submitForEnrollment();

		rateClassCards(enrollmentForm.getClassCards(), grade);
		return enrollmentForm;
	}

	private List<Subject> getSubjects(EnrollmentForm enrollmentForm) {
		List<Subject> subjects = new ArrayList<Subject>();
		for (Section section : enrollmentForm.getSections()) {
			subjects.add(section.getSubject());
		}

		return subjects;
	}

	private void rateClassCards(List<ClassCard> classCards, Grade grade) {
		for (ClassCard classCard : classCards) {
			classCard.setGrade(grade);
		}
	}
}
