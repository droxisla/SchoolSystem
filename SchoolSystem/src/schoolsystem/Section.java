package schoolsystem;

import java.util.*;

public class Section {
	private Subject subject;
	private Teacher teacher;
	private Schedule schedule;
	private List<ClassCard> classCards = new ArrayList<ClassCard>();
	
	public Section(Subject subject, Schedule schedule) {
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
