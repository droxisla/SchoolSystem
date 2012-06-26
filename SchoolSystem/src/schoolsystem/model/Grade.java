package schoolsystem.model;

import java.math.BigDecimal;

public enum Grade {

	NO_GRADE("0", false), 
	G1_00("1.00", true), 
	G1_25("1.25", true), 
	G1_50("1.50", true), 
	G1_75("1.75", true), 
	G2_00("2.00", true), 
	G2_25("2.25", true), 
	G2_50("2.50", true), 
	G2_75("2.75", true), 
	G3_00("3.00", true), 
	G5_00("5.00", false);
 
	private static BigDecimal bestPassingGrade;
	private static BigDecimal worstPassingGrade;

	static {
		for (Grade grade : Grade.values()) {
			if (grade.isPassing()) {
				BigDecimal gradeValue = grade.value();

				if (bestPassingGrade == null || bestPassingGrade.compareTo(gradeValue) > 0) {
					bestPassingGrade = gradeValue;
				}

				if (worstPassingGrade == null || worstPassingGrade.compareTo(gradeValue) < 0) {
					worstPassingGrade = gradeValue;
				}
			}
		}
	}

	private final BigDecimal value;
	private final boolean isPassing;

	Grade(String value, boolean isPassing) {
		this.value = new BigDecimal(value);
		this.isPassing = isPassing;
	}

	public BigDecimal value() {
		return value;
	}

	public boolean isPassing() {
		return isPassing;
	}

	public static boolean isPassing(BigDecimal average) {
		if (bestPassingGrade.compareTo(average) <= 0 && worstPassingGrade.compareTo(average) >= 0) {
			return true;
		}

		return false;
	}
}
