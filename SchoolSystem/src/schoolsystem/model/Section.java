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

	public Section(String name, Subject subject, Schedule schedule, Teacher teacher) throws ScheduleConflictException {
		SectionManager sectionManager = SectionManager.getInstance();

		this.name = name;
		this.subject = subject;
		this.schedule = schedule;
		this.teacher = teacher;

		if(sectionManager.hasSection(this)) {
			Section storedSection = sectionManager.getSection(this);
			this.classCards = storedSection.classCards;
		} else {
			this.classCards = new HashSet<ClassCard>();
			sectionManager.addSection(this);
		}
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
		StringBuilder sb = new StringBuilder();
		return sb.append(subject.toString()).append(" ").append(name).toString();
	}

	public String getSectionName() {
		return name;
	}

	void addClassCard(ClassCard classCard) {
		if (!classCard.getSection().equals(this)) {
			throw new IllegalArgumentException("Class card does not belong to this section");
		}

		assert classCards.size() <= MAX_STUDENTS : "Section capacity is full.";

		classCards.add(classCard);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((schedule == null) ? 0 : schedule.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
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
		Section other = (Section) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (schedule == null) {
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

	public boolean isFull() {
		int numberOfEnrolledStudents = classCards.size();
		return numberOfEnrolledStudents == MAX_STUDENTS;
	}

}