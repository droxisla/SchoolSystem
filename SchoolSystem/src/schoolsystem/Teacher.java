package schoolsystem;

import java.util.*;

public class Teacher {
	private List<Section> sections;
	private String name;
	
	public Teacher(String name) {
		this.name = name;
		sections = new ArrayList<Section>();
	}
	
	public void addSection(Section section) {
		sections.add(section);
	}
	
	public String toString() {
		return name;
	}
}
