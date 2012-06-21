package schoolsystem;

public class Student {
	private int studentNumber;
	private Status status;
	private Curriculum curriculum;
	
	public Student(int studentNumber, Status status, Curriculum curriculum) {
		this.studentNumber = studentNumber;
		this.status = status;
		this.curriculum = curriculum;
	}
	
	public enum Status {
		NEW, CONTINUING, GRADUATING, GRADUATED, PROBATIONARY, INELIGIBLE;
	}
}
