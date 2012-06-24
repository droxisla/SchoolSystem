package schoolsystem.model;

public enum StudentStatus {

	NEW(15, 18, true, true, false), 
	CONTINUING(18, 24, true, true, true), 
	GRADUATING(true, false, true), 
	GRADUATE(false, true, false),
	PROBATIONARY(true, true, true),
	INELIGIBLE(false, true, false);

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

	public boolean cankePrerequisiteSubjects() {
		return canTakePrerequisiteSubjects;
	}

}