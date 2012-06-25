package schoolsystem.model;

import java.util.ArrayList;
import java.util.List;

import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;

public class EnrollmentForm {

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

			if (!studentStatus.isEligibleToEnroll()) {
				throw new IneligibleStudentException();
			}

			this.sections = new ArrayList<Section>();
		}

		public EnrollmentFormBuilder addSection(Section section) throws SectionFullException,
				SubjectUnitsRestrictionException, ScheduleConflictException, UnsatisfiedPrerequisiteException {
			if (section == null) {
				throw new IllegalArgumentException("Section cannot be null.");
			}

			if (section.isFull()) {
				throw new SectionFullException();
			}

			checkScheduleConflict(section);
			checkRequiredPrerequisites(section.getSubject());
			addUnits(section);

			sections.add(section);

			return this;
		}

		private void checkRequiredPrerequisites(Subject subject) throws UnsatisfiedPrerequisiteException {
			if (subject.hasPrerequisites()) {
				if (!studentStatus.canTakePrerequisiteSubjects()) {
					throw new UnsatisfiedPrerequisiteException("Student cannot take subjects with prerequisite.");
				}
				
//				if(studentStatus.mustCheckPrerequisites()) {
//					student.hasTaken();
//				}
			}
		}

		private void addUnits(Section section) throws SubjectUnitsRestrictionException {
			Subject subject = section.getSubject();
			totalUnits += subject.getNumberOfUnits();

			if (totalUnits > studentStatus.getMaxUnits()) {
				throw new SubjectUnitsRestrictionException();
			}
		}

		private void checkScheduleConflict(Section section) throws ScheduleConflictException {
			final Section sectionWithSameSchedule = getSectionWithSameSchedule(section.getSchedule());
			if (sectionWithSameSchedule != null) {
				if (sectionWithSameSchedule.equals(section)) {
					throw new ScheduleConflictException("Section '" + section + "' is being enrolled twice.");
				}
				throw new ScheduleConflictException("Section '" + section + "' conflicts with other section schedule.");
			}
		}

		private Section getSectionWithSameSchedule(Schedule schedule) {
			for (Section section : sections) {
				if (section.getSchedule().equals(schedule)) {
					return section;
				}
			}
			return null; // TODO is it ok?
		}

		public EnrollmentForm enroll() throws SectionFullException, IneligibleStudentException,
				SubjectUnitsRestrictionException {
			if (totalUnits < studentStatus.getMinUnits()) {
				throw new SubjectUnitsRestrictionException();
			}

			return new EnrollmentForm(student, sections);
		}
	}

	private EnrollmentForm(Student student, List<Section> sections) {
		assert student != null;
		assert sections != null && !sections.isEmpty();

		this.sections = sections;
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
