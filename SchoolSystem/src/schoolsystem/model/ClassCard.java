package schoolsystem.model;

import java.math.BigDecimal;

public class ClassCard {

	private final Student student;
	private BigDecimal grade;
	private final Section section;

	ClassCard(EnrollmentForm enrollmentForm, Section section) {
		assert enrollmentForm != null;
		assert section != null;

		if (!enrollmentForm.hasSection(section)) {
			throw new IllegalArgumentException("Section '" + section + "' not present in enrollment form '"
					+ enrollmentForm + "'.");
		}

		this.section = section;
		this.student = enrollmentForm.getStudent();
		this.section.addClassCard(this);
	}

	public BigDecimal getGrade() {
		return grade;
	}

	public void setGrade(BigDecimal grade) {
		if (grade.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("Grades cannot be negative.");
		}
		this.grade = grade;
	}

	public Section getSection() {
		return section;
	}

	public Student getStudent() {
		return student;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((section == null) ? 0 : section.hashCode());
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
		ClassCard other = (ClassCard) obj;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}

}
