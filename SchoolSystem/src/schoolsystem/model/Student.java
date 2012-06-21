package schoolsystem.model;

import java.util.*;

public class Student {
	
	private final int studentNumber;
	private final Status status;
	private final Curriculum curriculum;
	private final List<EnrollmentForm> enrollmentForms;
	
	public Student(int studentNumber, Status status, Curriculum curriculum) {
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
	
	public Status getStatus() {
		return status;
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	@Override
	public String toString() {
		return "" + studentNumber;
	}
	
	public enum Status { //TODO so client will use Student.Status.New?, or should we place it separate java file?
		NEW, CONTINUING, GRADUATING, GRADUATED, PROBATIONARY, INELIGIBLE;
	}

	
}
