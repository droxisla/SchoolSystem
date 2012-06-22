package schoolsystem.model;

import schoolsystem.model.section.SectionFullException;


public class ClassCard {

	private final EnrollmentSection enrolledSection;
	private final EnrollmentForm enrollmentForm;

	public ClassCard(EnrollmentForm enrollmentForm, EnrollmentSection enrolledSection) throws SectionFullException {
		this.enrolledSection = enrolledSection;
		this.enrollmentForm = enrollmentForm;

		addClassCardToEnrolledSection();
		addClassCardToEnrollmentForm();
	}

	private void addClassCardToEnrollmentForm() {
		this.enrollmentForm.addClassCard(this);
	}

	public EnrollmentSection getSection() {
		return enrolledSection;
	}

	void addClassCardToEnrolledSection() throws SectionFullException {
		this.enrolledSection.addClassCard(this);
	}
}
