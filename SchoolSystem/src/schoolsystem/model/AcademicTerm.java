package schoolsystem.model;

import java.util.Calendar;

public class AcademicTerm {

	public final static int TERM_NUM_IN_DECEMBER = 2;
	public final static int MAX_TERM_NUM = 3;

	private final int year;
	private final int termNum;

	public static AcademicTerm currentAcademicTerm() {
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		int currentTermNum = getTermNum(currentMonth);

		return newInstance(currentYear, currentTermNum);
	}

	public static AcademicTerm academicTermAfterCurrent() {
		return currentAcademicTerm().getNextAcademicTerm();
	}

	public static AcademicTerm fromDate(int year, int month) {
		return newInstance(year, getTermNum(month));
	}

	public static AcademicTerm newInstance(int academicYear, int termNum) {
		if (termNum > MAX_TERM_NUM || termNum < 1) {
			throw new IllegalArgumentException("Term number value range must be from 1 to " + MAX_TERM_NUM);
		}
		return new AcademicTerm(academicYear, termNum);
	}

	private AcademicTerm(int academicYear, int termNumber) {
		this.year = academicYear;
		this.termNum = termNumber;
	}

	private static int getTermNum(int month) {
		if (month < 1) {
			throw new IllegalArgumentException("Month should be from 1 to 12");
		}

		if (month - 1 <= Calendar.MAY) {
			return 3;
		}

		if (month - 1 <= Calendar.AUGUST) {
			return 1;
		}

		if (month - 1 <= Calendar.DECEMBER) {
			return 2;
		}

		throw new IllegalArgumentException("Month should be from 1 to 12");
	}

	public AcademicTerm getNextAcademicTerm() {
		int newTermNum = getTermNum() + 1;
		int newAcademicYear = getYear();

		if (newTermNum > MAX_TERM_NUM) {
			newTermNum = 1;
		} else if (newTermNum > TERM_NUM_IN_DECEMBER) {
			newAcademicYear++;
		}

		return newInstance(newAcademicYear, newTermNum);
	}

	public int getYear() {
		return year;
	}

	public int getTermNum() {
		return termNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + termNum;
		result = prime * result + year;
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
		AcademicTerm other = (AcademicTerm) obj;
		if (termNum != other.termNum)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

}
