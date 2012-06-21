package schoolsystem.model;

public class ClassCard {

	private final Section section;
	private final EnrollmentForm enrollmentForm;

	public ClassCard(EnrollmentForm enrollmentForm, Section section) throws SectionFullException {
		this.section = section;
		this.enrollmentForm = enrollmentForm;

		addClassCardToSection();
		addClassCardToEnrollmentForm();
	}

	private void addClassCardToEnrollmentForm() {
		this.enrollmentForm.addClassCard(this);
	}

	public Section getSection() {
		return section;
	}

	void addClassCardToSection() throws SectionFullException {
		this.section.addClassCard(this);
	}
}
