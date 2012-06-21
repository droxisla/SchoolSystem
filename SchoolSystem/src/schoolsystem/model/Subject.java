package schoolsystem.model;

public class Subject {
	
	private static final long NUM_UNITS = 3;
	
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
	
	public long getNumberOfUnits() {
		return NUM_UNITS;
	}
}