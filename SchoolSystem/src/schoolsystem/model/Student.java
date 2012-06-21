package schoolsystem.model;

import java.util.*;

public class Student {

	private final int studentNumber;
	private final StudentStatus status;
	private final Curriculum curriculum;
	private final List<EnrollmentForm> enrollmentForms;

	public Student(int studentNumber, StudentStatus status, Curriculum curriculum) {
		this.enrollmentForms = new ArrayList<EnrollmentForm>();
		this.studentNumber = studentNumber;
		this.status = status;
		this.curriculum = curriculum;
	}

	public void addEnrollmentForm(EnrollmentForm enrollmentForm) {
		enrollmentForms.add(enrollmentForm);
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

}
