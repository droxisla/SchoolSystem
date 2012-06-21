package schoolsystem.model;

import java.util.ArrayList;
import java.util.List;

import schoolsystem.model.schedule.Schedule;

public class Teacher {
	
	private final List<Section> sections;
	private final String name;
	private final int facultyNumber;
	
	public Teacher(int facultyNumber, String name) {
		this.facultyNumber = facultyNumber;
		this.name = name;
		this.sections = new ArrayList<Section>();
	}
	
	public int getFacultyNumber() {
		return facultyNumber;
	}
	
	//TODO: Add getSection, make hasSection private
	public boolean hasSection(Section section) {
		return sections.contains(section);
	}
	
	public boolean hasScheduledClass(Schedule schedule) {
		return false; //TODO
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	void addSection(Section section) {
		sections.add(section);
	}
}
