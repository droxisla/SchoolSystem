package schoolsystem.model;

import java.math.BigDecimal;
import java.util.*;

import schoolsystem.model.EnrollmentForm.EnrollmentFormBuilder;

public class Student {

	private final int studentNumber;
	private StudentStatus status;
	private final Curriculum curriculum;
	private final List<EnrollmentForm> enrollmentForms;

	public Student(int studentNumber, StudentStatus status, Curriculum curriculum) {
		if (studentNumber < 0) {
			throw new IllegalArgumentException("Student number must not be negative.");
		}
		if (status == null) {
			throw new IllegalArgumentException("Student status must not be null.");
		}
		if (curriculum == null) {
			throw new IllegalArgumentException("Curriculum must not be null.");
		}

		this.enrollmentForms = new ArrayList<EnrollmentForm>();
		this.studentNumber = studentNumber;
		this.status = status;
		this.curriculum = curriculum;
	}

	public EnrollmentFormBuilder getEnrollmentFormBuilder() throws IneligibleStudentException {
		return new EnrollmentForm.EnrollmentFormBuilder(this);
	}

	void addEnrollmentForm(EnrollmentForm enrollmentForm) {
		assert enrollmentForm != null;

		if (!enrollmentForm.getStudent().equals(this)) {
			throw new IllegalArgumentException("Enrollment form does not belong to student.");
		}

		enrollmentForms.add(enrollmentForm);
	}

	private EnrollmentForm getCurrentEnrollmentForm() {
		if (enrollmentForms.isEmpty()) {
			return EnrollmentForm.BLANK_ENROLLMENT_FORM;
		}
		return enrollmentForms.get(enrollmentForms.size() - 1);
	}

	public void updateStatus() {
		if (classCardsHaveGrades()) {
			this.status = status.update(calculateAverage());
		}
	}

	private Boolean classCardsHaveGrades() {
		List<ClassCard> classCards = getCurrentEnrollmentForm().getClassCards();
		if (0 == classCards.size()) {
			return false;
		}
		for (ClassCard c : classCards) {
			if (null == c)
				return false;
		}
		return true;
	}

	public BigDecimal calculateAverage() {//TODO refactor as getLastTerm'sAverage
		BigDecimal average = BigDecimal.ZERO;
		
		List<ClassCard> classCards = getCurrentEnrollmentForm().getClassCards();
		for (ClassCard c : classCards) {
			Grade grade = c.getGrade();

			if (grade == Grade.NO_GRADE) {
				return BigDecimal.ZERO;
			}

			average = average.add(grade.value());
		}
		
		average = average.divide(new BigDecimal(classCards.size()), 2, BigDecimal.ROUND_CEILING);
		return average;
	}

	public int getNumEnrollmentForms() {
		return enrollmentForms.size();
	}

	public StudentStatus getStatus() {
		return status;
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	@Override
	public String toString() {
		return "" + studentNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + studentNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (studentNumber != other.studentNumber)
			return false;
		return true;
	}

	public int getStudentNumber() {
		return studentNumber;
	}

	public boolean hasPassedSubject(Subject subject) {
		for (EnrollmentForm enrollmentForm : enrollmentForms) {
			for (ClassCard classCard : enrollmentForm.getClassCards()) {
				if (classCard.hasPassedSubject(subject)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<EnrollmentForm> getEnrollmentForms() {
		return Collections.unmodifiableList(enrollmentForms);
	}

}
