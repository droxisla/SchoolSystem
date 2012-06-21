package schoolsystem.model;

import java.util.*;

public class Teacher {
	private List<Section> sections;
	private String name;
	
	public Teacher(String name) {
		this.name = name;
		sections = new ArrayList<Section>();
	}
	
	//TODO: Add getSection, make hasSection private
	public boolean hasSection(Section section) {
		return sections.contains(section);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	void addSection(Section section) {
		sections.add(section);
	}
}
