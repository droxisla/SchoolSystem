package schoolsystem.model;

import java.util.*;

import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;

public class Subject {

	private static final long NUM_UNITS = 3;

	private final List<Section> sections;
	private final List<Subject> prerequisites;
	private final String name;

	public Subject(String name) {
		this(name, new ArrayList<Subject>());
	}

	public Subject(String name, List<Subject> prerequisites) {
		this.sections = new ArrayList<Section>();
		this.name = name;
		this.prerequisites = new ArrayList<Subject>(prerequisites);
	}

	public boolean hasSection(String sectionName) {
		for (Section section : sections) {
			if (section.getSectionName().equalsIgnoreCase(sectionName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return name;
	}

	public List<Subject> getPrerequisites() {
		return new ArrayList<Subject>(prerequisites);
	}

	public long getNumberOfUnits() {
		return NUM_UNITS;
	}

	public Section createSection(String sectionName, Schedule schedule,
			Teacher teacher) throws ScheduleConflictException,
			SectionNameConflictException {
		return Section.createSection(sectionName, this, schedule, teacher);
	}

	public void addSection(Section section) {
		sections.add(section);
	}
}