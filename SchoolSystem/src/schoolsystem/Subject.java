package schoolsystem;

public class Subject {
	private String name;
	
	public Subject(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
