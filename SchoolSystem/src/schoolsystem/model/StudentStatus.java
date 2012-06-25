package schoolsystem.model;

import java.math.BigDecimal;

public enum StudentStatus {

	NEW(15, 18, true, true, false) {
		public StudentStatus update(int numEnrollmentForms, BigDecimal average) {
			if(numEnrollmentForms > 0 && PASSING_GRADE.compareTo(average) >= 0) {
				return CONTINUING;
			} else if(PASSING_GRADE.compareTo(average) < 0) {
				return PROBATIONARY;
			}
			return this;
		}; 
	},
	CONTINUING(18, 24, true, true, true) {
		public StudentStatus update(int numEnrollmentForms, BigDecimal average) {
			if(PASSING_GRADE.compareTo(average) < 0)
				return PROBATIONARY;
			return this;
		}; 
	}, 
	GRADUATING(true, false, true) {
		public StudentStatus update(int numEnrollmentForms, BigDecimal average) {
			return this;
		}; 
	},
	GRADUATE(false, true, false) {
		public StudentStatus update(int numEnrollmentForms, BigDecimal average) {
			return this;
		}; 
	},
	PROBATIONARY(true, true, true) {
		public StudentStatus update(int numEnrollmentForms, BigDecimal average) {
			if(PASSING_GRADE.compareTo(average) >= 0)
				return CONTINUING;
			else if(PASSING_GRADE.compareTo(average) < 0)
				return INELIGIBLE;
			return this;
		}; 
	},
	INELIGIBLE(false, true, false) {
		public StudentStatus update(int numEnrollmentForms, BigDecimal average) {
			return this;
		}; 
	};

	public static final BigDecimal PASSING_GRADE = new BigDecimal("3.00");
	private final int minUnits;
	private final int maxUnits;
	private final boolean isEligibleToEnroll;
	private final boolean mustCheckPrerequisites;
	private final boolean canTakePrerequisiteSubjects;

	StudentStatus(boolean isEligibleToEnroll, boolean mustCheckPrerequisites, boolean canTakeSubjectsWithPrerequisites) {
		this(1, Integer.MAX_VALUE, isEligibleToEnroll, mustCheckPrerequisites, canTakeSubjectsWithPrerequisites);
	}

	StudentStatus(int minUnits, int maxUnits, boolean isEligibleToEnroll, boolean mustCheckPrerequisites,
			boolean canTakePrerequisiteSubjects) {

		assert minUnits >= 0;
		assert maxUnits >= 0;

		this.minUnits = minUnits;
		this.maxUnits = maxUnits;
		this.isEligibleToEnroll = isEligibleToEnroll;
		this.mustCheckPrerequisites = mustCheckPrerequisites;
		this.canTakePrerequisiteSubjects = canTakePrerequisiteSubjects;
	}

	public int getMinUnits() {
		return minUnits;
	}

	public int getMaxUnits() {
		return maxUnits;
	}

	public boolean isEligibleToEnroll() {
		return isEligibleToEnroll;
	}

	public boolean mustCheckPrerequisites() {
		return mustCheckPrerequisites;
	}

	public boolean canTakePrerequisiteSubjects() {
		return canTakePrerequisiteSubjects;
	}
	
	public abstract StudentStatus update(int numEnrollmentForms, BigDecimal average);

}
