package schoolsystem.model;

import java.util.ArrayList;
import java.util.List;

import schoolsystem.model.section.SectionFullException;

public class EnrollmentSection {

	public static final int MAX_STUDENTS = 40;
	
	private final Section section;
	private final AcademicTerm academicTerm;
	private final List<ClassCard> classCards;
	
	static EnrollmentSection newInstance(Section section, AcademicTerm academicTerm) {
		EnrollmentSection enrollmentSection = section.findEnrollmentSectionDuring(academicTerm);
		boolean firstEnrollment = enrollmentSection == null;
		
		if (firstEnrollment) {
			enrollmentSection = new EnrollmentSection(section, academicTerm);
		}
		return enrollmentSection;
	}

	private EnrollmentSection(Section section, AcademicTerm academicTerm) {
		this.section = section;
		this.academicTerm = academicTerm;
		this.classCards = new ArrayList<ClassCard>();
		
		addToSectionEnrollmentHistory();
	}

	private void addToSectionEnrollmentHistory() {
		this.section.addEnrollmentHistory(academicTerm, this);
	}

	public Section getSection() {
		return section;
	}

	public AcademicTerm getAcademicTerm() {
		return academicTerm;
	}

	void addClassCard(ClassCard classCard) throws SectionFullException {
		int numberOfEnrolledStudents = classCards.size();

		if (numberOfEnrolledStudents < MAX_STUDENTS) {
			classCards.add(classCard);
		} else {
			throw new SectionFullException();
		}
	}

}
