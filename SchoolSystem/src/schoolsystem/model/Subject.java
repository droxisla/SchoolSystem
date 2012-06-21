package schoolsystem.model;

public class Subject {
	
	private final String name;
	private final Curriculum curriculum;
	
	public Subject(String name, Curriculum curriculum) {
		this.name = name;
		this.curriculum = curriculum;
	}
	
	@Override
	public String toString() {
		return name;
	}
}