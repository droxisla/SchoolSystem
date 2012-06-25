package schoolsystem.model;

public enum StudentStatus {

	NEW(15, 18, true, true, false) {
		public StudentStatus update(int numEnrollmentForms) {
			if(numEnrollmentForms > 0) {
				return CONTINUING;
			}
			return this;
		}; 
	},
	CONTINUING(18, 24, true, true, true) {
		public StudentStatus update(int numEnrollmentForms) {
			return this;
		}; 
	}, 
	GRADUATING(true, false, true) {
		public StudentStatus update(int numEnrollmentForms) {
			return this;
		}; 
	},
	GRADUATE(false, true, false) {
		public StudentStatus update(int numEnrollmentForms) {
			return this;
		}; 
	},
	PROBATIONARY(true, true, true) {
		public StudentStatus update(int numEnrollmentForms) {
			return this;
		}; 
	},
	INELIGIBLE(false, true, false) {
		public StudentStatus update(int numEnrollmentForms) {
			return this;
		}; 
	};

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
	
	public abstract StudentStatus update(int numEnrollmentForms);

}