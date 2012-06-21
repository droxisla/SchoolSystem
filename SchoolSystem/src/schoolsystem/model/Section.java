package schoolsystem.model;

import java.util.*;

import schoolsystem.model.schedule.Schedule;

public class Section {
	
	public static final int MAX_STUDENTS = 40;
	
	private final String name;
	private final Subject subject;
	private final Teacher teacher;
	private final Schedule schedule;
	private final List<ClassCard> classCards;
	
	public Section(String name, Subject subject, Schedule schedule, Teacher teacher) {
		this.classCards = new ArrayList<ClassCard>();
		this.name = name;
		this.subject = subject;
		this.schedule = schedule;
		this.teacher = teacher;		
				
		addSectionToTeacher();
	}
	
	private void addSectionToTeacher() {
		this.teacher.addSection(this);
	}

	void addClassCard(ClassCard classCard) throws SectionFullException {
		int numberOfEnrolledStudents = classCards.size();
		
		if(numberOfEnrolledStudents < MAX_STUDENTS) {
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
		StringBuilder sb = new StringBuilder(); //TODO i placed it here, though I think stringbuilder is not necessary, given the short string result
		return sb.append(subject.toString()).append(" ").append(name).toString();
	}
		
}