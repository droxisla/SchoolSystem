package schoolsystem.model;

import java.util.HashSet;
import java.util.Set;

import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;

public class Section {

	public static final int MAX_STUDENTS = 40;

	private final String name;
	private final Subject subject;
	private final Schedule schedule;
	private final Teacher teacher;
	private final Set<ClassCard> classCards;
	private final int sectionId;

	public Section(int sectionId, String name, Subject subject, Schedule schedule, Teacher teacher)
			throws ScheduleConflictException {

		if (sectionId < 0) {
			throw new IllegalArgumentException("Section id must not be negative.");
		}
		if (name == null) {
			throw new IllegalArgumentException("Section name must not be null.");
		}
		if (subject == null) {
			throw new IllegalArgumentException("Subject must not be null.");
		}
		if (schedule == null) {
			throw new IllegalArgumentException("Schedule must not be null.");
		}
		if (teacher == null) {
			throw new IllegalArgumentException("Teacher must not be null.");
		}

		this.teacher = teacher;
		this.sectionId = sectionId;
		this.name = name;
		this.subject = subject;
		this.schedule = schedule;
		this.classCards = new HashSet<ClassCard>();

		if (teacher.hasScheduledClass(schedule)) {
			boolean scheduledClassIsSameSection = teacher.hasSection(this);

			if (!scheduledClassIsSameSection) {
				throw new ScheduleConflictException("Schedule conflict for a teacher when adding the section.");
			}
		}
		
		teacher.addSection(this);
	}

	public Subject getSubject() {
		return subject;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	@Override
	public String toString() {
		return subject + " " + name;
	}

	public String getSectionName() {
		return name;
	}

	void addClassCard(ClassCard classCard) {
		assert classCard != null;
		assert classCards.size() <= MAX_STUDENTS : "Section capacity is full.";

		if (!classCard.getSection().equals(this)) {
			throw new IllegalArgumentException("Class card does not belong to this section");
		}

		classCards.add(classCard);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + sectionId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Section))
			return false;
		Section other = (Section) obj;
		if (sectionId != other.sectionId)
			return false;
		return true;
	}

	public boolean isFull() {
		int numberOfEnrolledStudents = classCards.size();
		return numberOfEnrolledStudents == MAX_STUDENTS;
	}

	public int getSectionId() {
		return sectionId;
	}

}