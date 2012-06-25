package schoolsystem.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import schoolsystem.model.EnrollmentForm.EnrollmentFormBuilder;

public class Student {

	private final int studentNumber;
	private StudentStatus status;
	private final Curriculum curriculum;
	private final List<EnrollmentForm> enrollmentForms;
	private EnrollmentForm currentEnrollmentForm;

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
		currentEnrollmentForm = enrollmentForm;
	}
	
	public void updateStatus() {
		this.status = status.update(enrollmentForms.size());
	}
	
	public BigDecimal calculateAverage() {
		BigDecimal average = BigDecimal.ZERO;
		List<ClassCard> classCards = currentEnrollmentForm.getClassCards();
		for(ClassCard c: classCards) {
			average = average.add(c.getGrade());
		}
		average = average.divide(new BigDecimal("" + classCards.size()));
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

}
