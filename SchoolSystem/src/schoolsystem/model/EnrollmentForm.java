package schoolsystem.model;

import java.util.*;

public class EnrollmentForm {
	
	private final Student student;
	private final List<ClassCard> classCards;
	
	public EnrollmentForm(Student student) {
		this.classCards = new ArrayList<ClassCard>();
		this.student = student;
		
		addEnrollmentFormToStudent();
	}
	
	private void addEnrollmentFormToStudent() {
		this.student.addEnrollmentForm(this);
	}

	public Student getStudent() {
		return student;
	}

	void addClassCard(ClassCard classCard) {
		this.classCards.add(classCard);
	}
}
