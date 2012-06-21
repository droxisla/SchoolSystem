package schoolsystem.model;

public class Subject {
	
	private static final long NUM_UNITS = 3;
	
	private final String name;
	
	public Subject(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public long getNumberOfUnits() {
		return NUM_UNITS;
	}
}