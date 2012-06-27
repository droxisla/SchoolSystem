package schoolsystem.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import schoolsystem.model.schedule.AcademicTerm;

public class Student {

	private final int studentNumber;
	private final StudentStatus status;
	private final Curriculum curriculum;
	private final List<EnrollmentForm> enrollmentForms;
	private final String studentName;

	public Student(int studentNumber, String studentName, StudentStatusType statusType, Curriculum curriculum) {
		if (studentNumber < 0) {
			throw new IllegalArgumentException("Student number must not be negative.");
		}
		if (statusType == null) {
			throw new IllegalArgumentException("Student status must not be null.");
		}
		if (curriculum == null) {
			throw new IllegalArgumentException("Curriculum must not be null.");
		}
		if (studentName == null) {
			throw new IllegalArgumentException("Student name must not be null.");
		}

		this.studentName = studentName;
		this.enrollmentForms = new ArrayList<EnrollmentForm>();
		this.studentNumber = studentNumber;
		this.status = new StudentStatus(statusType);
		this.curriculum = curriculum;
	}

	public EnrollmentForm getNewEnrollmentForm() throws IneligibleStudentException {
		return new EnrollmentForm(this);
	}

	void addEnrollmentForm(EnrollmentForm enrollmentForm) {
		assert enrollmentForm != null;

		if (!enrollmentForm.getStudent().equals(this)) {
			throw new IllegalArgumentException("Enrollment form does not belong to student.");
		}

		enrollmentForms.add(enrollmentForm);
	}

	public void updateStatus(AcademicTerm currentAcademicTerm) {
		if (currentAcademicTerm == null) {
			throw new IllegalArgumentException("Current academic term must not be null.");
		}

		boolean studentStatusIsOld = status.statusNeedsUpdate(currentAcademicTerm);

		if (studentStatusIsOld && allCurrentTermClassCardsHaveGrades()) {
			TermStatus termStatus = new TermStatus(calculateCurrentTermAverage(), getRemainingUnits(),
					prerequisitesDone());
			status.updateStatus(currentAcademicTerm, termStatus);
		}
	}

	private int getRemainingUnits() {
		int units = curriculum.getTotalUnits();
		List<Subject> subjects = curriculum.getSubjects();
		for (Subject s : subjects) {
			if (hasPassedSubject(s)) {
				units -= 3;
			}
		}
		return units;
	}

	private boolean prerequisitesDone() {
		Set<Subject> prereqs = curriculum.getPrerequisites();
		for (Subject p : prereqs) {
			if (!hasPassedSubject(p)) {
				return false;
			}
		}
		return true;
	}

	boolean allCurrentTermClassCardsHaveGrades() {
		if (enrollmentForms.isEmpty()) {
			return false;
		}

		EnrollmentForm currentTermEnrollmentForm = enrollmentForms.get(enrollmentForms.size() - 1);
		return currentTermEnrollmentForm.allClassCardsHaveGrades();
	}

	public BigDecimal calculateCurrentTermAverage() {
		if (enrollmentForms.isEmpty() || !allCurrentTermClassCardsHaveGrades()) {
			return BigDecimal.ZERO;
		}

		BigDecimal average = BigDecimal.ZERO;

		EnrollmentForm lastTermEnrollmentForm = enrollmentForms.get(enrollmentForms.size() - 1);
		List<ClassCard> classCards = lastTermEnrollmentForm.getClassCards();

		for (ClassCard c : classCards) {
			Grade grade = c.getGrade();
			average = average.add(grade.value());
		}

		return average.divide(new BigDecimal(classCards.size()), 2, BigDecimal.ROUND_CEILING);
	}

	public int getNumEnrollmentForms() {
		return enrollmentForms.size();
	}

	public StudentStatusType getStatusType() {
		return status.getStatusType();
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	@Override
	public String toString() {
		return studentName + " (" + studentNumber + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + studentNumber;
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
		Student other = (Student) obj;
		if (studentNumber != other.studentNumber)
			return false;
		return true;
	}

	public int getStudentNumber() {
		return studentNumber;
	}

	public boolean hasPassedSubject(Subject subject) {
		if (subject == null) {
			return false;
		}

		for (EnrollmentForm enrollmentForm : enrollmentForms) {
			for (ClassCard classCard : enrollmentForm.getClassCards()) {
				if (classCard.hasPassedSubject(subject)) {
					return true;
				}
			}
		}
		return false;
	}

	public List<EnrollmentForm> getSubmittedEnrollmentForms() {
		return Collections.unmodifiableList(enrollmentForms);
	}

	AcademicTerm getStatusAcademicTerm() {
		return status.getStatusTime();
	}

}
