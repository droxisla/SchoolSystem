package schoolsystem.model;

import java.util.HashMap;
import java.util.Map;

import schoolsystem.model.schedule.Schedule;

public class Teacher {

	private final String name;
	private final int facultyNumber;
	private final Map<Schedule, Section> scheduledSections;

	public Teacher(int facultyNumber, String name) {
		if (name == null) {
			throw new IllegalArgumentException("Teacher's name must not be null.");
		}
		if (facultyNumber < 0) {
			throw new IllegalArgumentException("Faculty number must not be negative.");
		}

		this.scheduledSections = new HashMap<Schedule, Section>();
		this.facultyNumber = facultyNumber;
		this.name = name;
	}

	public boolean hasScheduledClass(Schedule schedule) {
		return scheduledSections.containsKey(schedule);
	}

	public boolean hasSection(Section section) {
		Schedule schedule = section.getSchedule();
		if (hasScheduledClass(schedule)) {
			return scheduledSections.get(schedule).equals(section);
		}
		return false;
	}

	void addSection(Section section) {
		assert section != null;

		if (!section.getTeacher().equals(this)) {
			throw new IllegalArgumentException("Section being added does not belong to the teacher");
		}

		scheduledSections.put(section.getSchedule(), section);
	}

	public int getFacultyNumber() {
		return facultyNumber;
	}

	@Override
	public String toString() {
		return name + " (" + facultyNumber + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + facultyNumber;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		if (facultyNumber != other.facultyNumber)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
