package schoolsystem.model;

import java.util.*;

public class Section {
	public static final int MAX_STUDENTS = 40;
	
	private String name;
	private Subject subject;
	private Teacher teacher;
	private Schedule schedule;
	private List<ClassCard> classCards;
	private int enrolledStudents;
	private StringBuilder sb;
	
	public Section(String name, Subject subject, Schedule schedule, Teacher teacher) {
		classCards = new ArrayList<ClassCard>();
		this.name = name;
		this.subject = subject;
		this.schedule = schedule;
		this.teacher = teacher;
		teacher.addSection(this);
		enrolledStudents = 0;
		sb = new StringBuilder();
	}
	
	public void addClassCard(ClassCard classCard) throws SectionFullException {
		if(enrolledStudents < MAX_STUDENTS) {
			classCards.add(classCard);
			enrolledStudents++;
		} else {
			throw new SectionFullException();
		}
	}
	
	public Subject getSubject() {
		return subject;
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	
	public Schedule getSchedule() {
		return schedule;
	}
	
	@Override
	public String toString() {
		return sb.append(subject.toString()).append(" ").append(name).toString();
	}
		
}