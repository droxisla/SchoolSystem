package schoolsystem.model;

import java.util.HashMap;
import java.util.Map;

import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;
import schoolsystem.model.section.SectionNameConflictException;

public class Section {

	private final String name;
	private final Subject subject;
	private final Teacher teacher;
	private final Schedule schedule;
	private final Map<AcademicTerm, EnrollmentSection> enrollmentHistory;

	static Section createSection(String sectionName, Subject subject, Schedule schedule, Teacher teacher)
			throws ScheduleConflictException, SectionNameConflictException {
		if (teacher.hasScheduledClassDuring(schedule)) {
			throw new ScheduleConflictException();
		}

		if (subject.hasSection(sectionName)) {
			throw new SectionNameConflictException();
		}

		return new Section(sectionName, subject, schedule, teacher);
	}

	private Section(String name, Subject subject, Schedule schedule, Teacher teacher) {
		this.name = name;
		this.subject = subject;
		this.schedule = schedule;
		this.teacher = teacher;
		this.enrollmentHistory = new HashMap<AcademicTerm, EnrollmentSection>();

		addSectionToTeacher();
		addSectionToSubject();
	}

	private void addSectionToSubject() {
		this.subject.addSection(this);
	}

	private void addSectionToTeacher() {
		this.teacher.addSection(this);
	}

	public Subject getSubject() {
		return subject;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append(subject.toString()).append(" ").append(name).toString();
	}

	public String getSectionName() {
		return name;
	}

	public EnrollmentSection openEnrollmentForNextAcademicTerm() {
		AcademicTerm nextAcademicTerm = AcademicTerm.academicTermAfterCurrent();
		return EnrollmentSection.newInstance(this, nextAcademicTerm);
	}

	EnrollmentSection findEnrollmentSectionDuring(AcademicTerm academicTerm) {
		return enrollmentHistory.get(academicTerm);
		//TODO if none, then null ko na lang haha
	}

	void addEnrollmentHistory(AcademicTerm academicTerm, EnrollmentSection enrollmentSection) {
		assert enrollmentHistory.containsKey(academicTerm):"Double enrollment section entries";
		enrollmentHistory.put(academicTerm, enrollmentSection);
	}

}