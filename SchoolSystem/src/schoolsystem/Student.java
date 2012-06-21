package schoolsystem;

import java.util.*;

public class Student {
	private int studentNumber;
	private Status status;
	private Curriculum curriculum;
	private List<EnrollmentForm> enrollmentForms;
	
	public Student(int studentNumber, Status status, Curriculum curriculum) {
		enrollmentForms = new ArrayList<EnrollmentForm>();
		this.studentNumber = studentNumber;
		this.status = status;
		this.curriculum = curriculum;
	}
	
	public void addEnrollmentForm(EnrollmentForm enrollmentForm) {
		enrollmentForms.add(enrollmentForm);
	}
	
	public enum Status {
		NEW, CONTINUING, GRADUATING, GRADUATED, PROBATIONARY, INELIGIBLE;
	}
}
