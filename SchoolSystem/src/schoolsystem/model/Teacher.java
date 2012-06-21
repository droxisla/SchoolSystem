package schoolsystem.model;

import java.util.*;

public class Teacher {
	private final List<Section> sections;
	private final String name;
	
	public Teacher(String name) {
		this.name = name;
		this.sections = new ArrayList<Section>();
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
