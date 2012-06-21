package schoolsystem.model;

public class Subject {
	
	private final String name;
	private final Curriculum curriculum;
	private final int load;
	
	public Subject(String name, Curriculum curriculum) {
		this.name = name;
		this.curriculum = curriculum;
		load = 3;
	}
	
	@Override
	public String toString() {
		return name;
	}
}