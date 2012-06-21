package schoolsystem;

import java.util.*;

public class Section {
	private Subject subject;
	private Teacher teacher;
	private Schedule schedule;
	private List<ClassCard> classCards;
	
	public Section(Subject subject, Schedule schedule) {
		classCards = new ArrayList<ClassCard>();
		this.subject = subject;
		this.schedule = schedule;
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public void addClassCard(ClassCard classCard) {
		classCards.add(classCard);
	}
}
