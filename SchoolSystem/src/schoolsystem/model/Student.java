package schoolsystem.model;

import java.util.ArrayList;
import java.util.List;

import schoolsystem.model.EnrollmentForm.EnrollmentFormBuilder;

public class Student {

	private final int studentNumber;
	private StudentStatus status;
	private final Curriculum curriculum;
	private final List<EnrollmentForm> enrollmentForms;

	public Student(int studentNumber, StudentStatus status, Curriculum curriculum) {
		this.enrollmentForms = new ArrayList<EnrollmentForm>();
		this.studentNumber = studentNumber;
		this.status = status;
		this.curriculum = curriculum;
	}

	public EnrollmentFormBuilder getEnrollmentFormBuilder() throws IneligibleStudentException {
		return new EnrollmentForm.EnrollmentFormBuilder(this);
	}

	void addEnrollmentForm(EnrollmentForm enrollmentForm) {
		if (!enrollmentForm.getStudent().equals(this)) {
			throw new IllegalArgumentException("Enrollment form does not belong to student.");
		}
		enrollmentForms.add(enrollmentForm);
	}
	
	public void updateStatus() {
		this.status = status.update(enrollmentForms.size());
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
