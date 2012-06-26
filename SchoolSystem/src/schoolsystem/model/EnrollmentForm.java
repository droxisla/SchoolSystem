package schoolsystem.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;

public class EnrollmentForm {
	// TODO may be dangerous?
	public static final EnrollmentForm BLANK_ENROLLMENT_FORM = new EnrollmentForm();

	private final Student student;
	private final List<ClassCard> classCards;
	private final List<Section> sections;

	static class EnrollmentFormBuilder {
		private final Student student;
		private final StudentStatus studentStatus;
		private final List<Section> sections;
		private int totalUnits;

		EnrollmentFormBuilder(Student student) throws IneligibleStudentException {
			assert student != null;

			this.student = student;
			this.totalUnits = 0;
			this.studentStatus = student.getStatus();
			this.sections = new ArrayList<Section>();

			if (!studentStatus.isEligibleToEnroll()) {
				throw new IneligibleStudentException();
			}

			checkIfPreviousTermHasAverage();
		}

		private void checkIfPreviousTermHasAverage() {
			boolean enrolledLastTerm = student.getNumEnrollmentForms() > 0;
			if (enrolledLastTerm) {
				boolean previousTermSubjectsHaveGrade = student.calculateAverage().equals(BigDecimal.ZERO);

				if (previousTermSubjectsHaveGrade) {
					throw new IllegalStateException(
							"Student cannot enroll since some of the class cards from previous term has no grade.");
				}
			}
		}

		public EnrollmentFormBuilder addSection(Section section) throws SectionFullException,
				SubjectUnitsRestrictionException, ScheduleConflictException, UnsatisfiedPrerequisiteException {
			if (section == null) {
				throw new IllegalArgumentException("Section cannot be null.");
			}

			if (section.isFull()) {
				throw new SectionFullException();
			}
			
			final Subject subject = section.getSubject();
			
			if(student.hasPassedSubject(subject)) {
				throw new SubjectUnitsRestrictionException("The subject '"+subject+"' has been passed in previous terms.");
			}

			checkScheduleConflict(section);
			checkRequiredPrerequisites(subject);
			addUnits(section);

			sections.add(section);

			return this;
		}

		private void checkRequiredPrerequisites(Subject subject) throws UnsatisfiedPrerequisiteException {
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

		private void addUnits(Section section) throws SubjectUnitsRestrictionException {
			Subject subject = section.getSubject();
			totalUnits += subject.getNumberOfUnits();

			if (totalUnits > studentStatus.getMaxUnits()) {
				throw new SubjectUnitsRestrictionException();
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

		public EnrollmentForm enroll() throws SectionFullException, IneligibleStudentException,
				SubjectUnitsRestrictionException {
			if (totalUnits < studentStatus.getMinUnits()) {
				throw new SubjectUnitsRestrictionException();
			}

			return new EnrollmentForm(student, sections);
		}
	}

	private EnrollmentForm() {
		sections = Collections.emptyList();
		classCards = Collections.emptyList();
		student = null;
	}

	private EnrollmentForm(Student student, List<Section> sections) {
		assert student != null;
		assert sections != null && !sections.isEmpty();

		this.sections = Collections.unmodifiableList(sections);
		this.student = student;
		this.classCards = new ArrayList<ClassCard>();

		for (Section section : sections) {
			classCards.add(new ClassCard(this, section));
		}

		this.student.addEnrollmentForm(this);
	}

	public Student getStudent() {
		return student;
	}

	public List<ClassCard> getClassCards() {
		return classCards;
	}

	public List<Section> getSections() {
		return sections;
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

}
