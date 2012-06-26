package schoolsystem.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;

public class EnrollmentForm {

	private final Student student;
	private final List<ClassCard> classCards;
	private final List<Section> sections;
	private int totalUnits;

	public EnrollmentForm(Student student) throws IneligibleStudentException {
		if (student == null) {
			throw new IllegalArgumentException("Student must not be null.");
		}

		this.totalUnits = 0;
		this.student = student;
		this.classCards = new ArrayList<ClassCard>();
		this.sections = new ArrayList<Section>();

		if (!student.getStatus().isEligibleToEnroll()) {
			throw new IneligibleStudentException();
		}

		checkIfPreviousTermHasAverage();
		
		student.updateStatus();
	}

	private void checkIfPreviousTermHasAverage() {
		boolean enrolledLastTerm = student.getNumEnrollmentForms() > 0;
		if (enrolledLastTerm) {
			boolean previousTermSubjectsHaveGrade = student.calculateLastTermAverage().equals(BigDecimal.ZERO);

			if (previousTermSubjectsHaveGrade) {
				throw new IllegalStateException(
						"Student cannot enroll since some of the class cards from previous term has no grade.");
			}
		}
	}

	public void addSection(Section section) throws SectionFullException, SubjectUnitsRestrictionException,
			ScheduleConflictException, UnsatisfiedPrerequisiteException {
		
		if (hasBeenSubmittedForEnrollment()) {
			throw new IllegalStateException("Enrollment form has been submitted already.");
		}

		if (section == null) {
			throw new IllegalArgumentException("Section cannot be null.");
		}

		if (section.isFull()) {
			throw new SectionFullException();
		}

		Subject subject = section.getSubject();

		if (student.hasPassedSubject(subject)) {
			throw new SubjectUnitsRestrictionException("The subject '" + subject
					+ "' has been passed in previous terms.");
		}

		checkDuplicateSubject(subject);
		checkScheduleConflict(section);
		checkRequiredPrerequisites(subject);
		
		updateTotalUnits(section);
		sections.add(section);
	}

	private void checkDuplicateSubject(Subject subject) throws SubjectUnitsRestrictionException {
		for (Section section : sections) {
			if (section.getSubject().equals(subject)) {
				throw new SubjectUnitsRestrictionException("Student is enrolling the same subject twice.");
			}
		}
	}

	private void checkScheduleConflict(Section newSection) throws ScheduleConflictException {
		Schedule schedule = newSection.getSchedule();

		for (Section addedSection : sections) {
			if (addedSection.getSchedule().equals(schedule)) {
				if (addedSection.equals(newSection)) {
					throw new ScheduleConflictException("Section '" + addedSection + "' is being enrolled twice.");
				}
				throw new ScheduleConflictException("Section '" + addedSection
						+ "' conflicts with other section schedule.");
			}
		}
	}

	private void checkRequiredPrerequisites(Subject subject) throws UnsatisfiedPrerequisiteException {
		StudentStatus studentStatus = student.getStatus();

		if (subject.hasPrerequisites()) {
			if (!studentStatus.canTakeSubjectsWithPrerequisite()) {
				throw new UnsatisfiedPrerequisiteException("Student cannot take subjects with prerequisite.");
			}

			if (studentStatus.mustCheckPrerequisites()) {
				for (Subject prereqSubject : subject.getPrerequisites()) {
					if (!student.hasPassedSubject(prereqSubject)) {
						throw new UnsatisfiedPrerequisiteException("The prerequisite subject '" + prereqSubject
								+ "' of '" + subject + "' must be passed first.");
					}
				}
			}
		}
	}

	private void updateTotalUnits(Section section) throws SubjectUnitsRestrictionException {
		StudentStatus studentStatus = student.getStatus();

		Subject subject = section.getSubject();
		totalUnits += subject.getNumberOfUnits();

		if (totalUnits > studentStatus.getMaxUnits()) {
			throw new SubjectUnitsRestrictionException();
		}
	}

	public void submitForEnrollment() throws SubjectUnitsRestrictionException {
		StudentStatus studentStatus = student.getStatus();
		int minUnits = studentStatus.getMinUnits();

		if (totalUnits < minUnits) {
			throw new SubjectUnitsRestrictionException("Student must take at least " + studentStatus.getMinUnits()
					+ " number of units.");
		}

		if (!hasBeenSubmittedForEnrollment()) {
			this.student.addEnrollmentForm(this);

			for (Section section : sections) {
				classCards.add(new ClassCard(this, section));
			}
		}
	}

	private boolean hasBeenSubmittedForEnrollment() {
		return !this.classCards.isEmpty();
	}

	public Student getStudent() {
		return student;
	}

	public List<ClassCard> getClassCards() {
		return classCards;
	}

	public List<Section> getSections() {
		return Collections.unmodifiableList(sections);
	}

	public boolean hasSection(Section section) {
		return sections.contains(section);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sections == null) ? 0 : sections.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnrollmentForm other = (EnrollmentForm) obj;
		if (sections == null) {
			if (other.sections != null)
				return false;
		} else if (!sections.equals(other.sections))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EnrollmentForm [classCards=" + classCards + "]";
	}
	
}
