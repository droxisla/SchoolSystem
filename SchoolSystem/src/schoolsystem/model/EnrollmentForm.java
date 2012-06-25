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

		public EnrollmentFormBuilder addSection(Section section) throws SectionFullException, SubjectUnitsRestrictionException, ScheduleConflictException {
			if(section==null) {
				throw new IllegalArgumentException("Section cannot be null.");
			}
			
			if (section.isFull()) {
				throw new SectionFullException();
			}

			checkScheduleConflict(section);
			addUnits(section);
			
			sections.add(section);

			return this;
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

		public EnrollmentForm enroll() throws SectionFullException, IneligibleStudentException, SubjectUnitsRestrictionException {
			if (totalUnits < studentStatus.getMinUnits()) {
				throw new SubjectUnitsRestrictionException();
			}

			return new EnrollmentForm(student, sections);
		}
	}

	private EnrollmentForm(Student student, List<Section> sections) {
		assert student != null;
		assert sections!=null && !sections.isEmpty();
		
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
}
