package schoolsystem;

import java.util.*;
public class EnrollmentForm {
	private Student student;
	private List<ClassCard> classCards = new ArrayList<ClassCard>();
	
	public EnrollmentForm(Student student) {
		this.student = student;
	}
	
	public void addClassCard(ClassCard classCard) {
		classCards.add(classCard);
	}
}
