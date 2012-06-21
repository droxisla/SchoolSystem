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
	
	public EnrollmentForm addNewEnrollmentForm() {
		EnrollmentForm ef = new EnrollmentForm(this);
		enrollmentForms.add(ef);
		return ef;
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
	
	public enum Status {
		NEW, CONTINUING, GRADUATING, GRADUATED, PROBATIONARY, INELIGIBLE;
	}
}
