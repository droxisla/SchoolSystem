package schoolsystem.model;

import java.util.ArrayList;
import java.util.List;

import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;

public class Section {

	public static final int MAX_STUDENTS = 40;

	private final String name;
	private final Subject subject;
	private final Teacher teacher;
	private final Schedule schedule;
	private final List<ClassCard> classCards;

	static Section createSection(String sectionName, Subject subject, Schedule schedule, Teacher teacher)
			throws ScheduleConflictException, SectionNameConflictException {
		if (teacher.hasScheduledClassDuring(schedule)) {
			throw new ScheduleConflictException();
		}

		if (subject.hasSection(sectionName)) {
			throw new SectionNameConflictException();
		}

		return new Section(sectionName, subject, schedule, teacher);
	}

	private Section(String name, Subject subject, Schedule schedule, Teacher teacher) {
		this.classCards = new ArrayList<ClassCard>();
		this.name = name;
		this.subject = subject;
		this.schedule = schedule;
		this.teacher = teacher;

		addSectionToTeacher();
		addSectionToSubject();
	}

	private void addSectionToSubject() {
		this.subject.addSection(this);
	}

	private void addSectionToTeacher() {
		this.teacher.addSection(this);
	}

	void addClassCard(ClassCard classCard) throws SectionFullException {
		int numberOfEnrolledStudents = classCards.size();

		if (numberOfEnrolledStudents < MAX_STUDENTS) {
			classCards.add(classCard);
		} else {
			throw new SectionFullException();
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

}