package schoolsystem.model;

import java.util.*;
public class EnrollmentForm {
	private Student student;
	private List<ClassCard> classCards;
	
	public EnrollmentForm(Student student) {
		classCards = new ArrayList<ClassCard>();
		this.student = student;
	}
	
	public void addClassCard(ClassCard classCard) {
		classCards.add(classCard);
	}
	
	public Student getStudent() {
		return student;
	}
}
