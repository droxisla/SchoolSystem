package schoolsystem;

import java.util.*;

public class Section {
	private Subject subject;
	private Teacher teacher;
	private Schedule schedule;
	private List<ClassCard> classCards;
	
	public Section(Subject subject, Schedule schedule, Teacher teacher) {
		classCards = new ArrayList<ClassCard>();
		this.subject = subject;
		this.schedule = schedule;
		this.teacher = teacher;
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public void addClassCard(ClassCard classCard) {
		classCards.add(classCard);
	}
}
