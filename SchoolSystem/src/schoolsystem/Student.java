package schoolsystem;

public class Student {
	private int studentNumber;
	private Status status;
	
	public Student(int studentNumber, Status status) {
		this.studentNumber = studentNumber;
		this.status = status;
	}
	
	public enum Status {
		NEW, CONTINUING, GRADUATING, GRADUATED, PROBATIONARY, INELIGIBLE;
	}
}
