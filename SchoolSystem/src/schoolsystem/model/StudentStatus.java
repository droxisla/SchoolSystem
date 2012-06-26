package schoolsystem.model;

import java.math.BigDecimal;

public enum StudentStatus {

	NEW() {
		public StudentStatus update(BigDecimal average, int unitsLeft, boolean prerequisitesDone) {
			if (average.equals(BigDecimal.ZERO)) {
				return this;
			} else if (Grade.isPassing(average)) {
				return CONTINUING;
			} else {
				return PROBATIONARY;
			}
		}

		@Override
		public int getMinUnits() {
			return 15;
		}

		@Override
		public int getMaxUnits() {
			return 18;
		}

		@Override
		public boolean isEligibleToEnroll() {
			return true;
		}

		@Override
		public boolean mustCheckPrerequisites() {
			return true;
		}

		@Override
		public boolean canTakePrerequisiteSubjects() {
			return false;
		};
	},
	CONTINUING() {
		public StudentStatus update(BigDecimal average, int unitsLeft, boolean prerequisitesDone) {
			if (average.equals(BigDecimal.ZERO)) {
				return this;
			} else if (!Grade.isPassing(average)) {
				return PROBATIONARY;
			} else if (unitsLeft <= 18 && prerequisitesDone) {
				return GRADUATING;
			}
			return this;
		}

		@Override
		public int getMinUnits() {
			return 18;
		}

		@Override
		public int getMaxUnits() {
			return 24;
		}

		@Override
		public boolean isEligibleToEnroll() {
			return true;
		}

		@Override
		public boolean mustCheckPrerequisites() {
			return true;
		}

		@Override
		public boolean canTakePrerequisiteSubjects() {
			return true;
		};
	},
	GRADUATING() {
		@Override
		public StudentStatus update(BigDecimal average, int unitsLeft, boolean prerequisitesDone) {
			return this;
		}

		@Override
		public int getMinUnits() {
			return 1;
		}

		@Override
		public int getMaxUnits() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isEligibleToEnroll() {
			return true;
		}

		@Override
		public boolean mustCheckPrerequisites() {
			return false;
		}

		@Override
		public boolean canTakePrerequisiteSubjects() {
			return true;
		};
	},
	GRADUATE() {
		@Override
		public StudentStatus update(BigDecimal average, int unitsLeft, boolean prerequisitesDone) {
			return this;
		}

		@Override
		public int getMinUnits() {
			return 1;
		}

		@Override
		public int getMaxUnits() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isEligibleToEnroll() {
			return false;
		}

		@Override
		public boolean mustCheckPrerequisites() {
			return true;
		}

		@Override
		public boolean canTakePrerequisiteSubjects() {
			return false;
		};
	},
	PROBATIONARY() {
		@Override
		public StudentStatus update(BigDecimal average, int unitsLeft, boolean prerequisitesDone) {
			if (average.equals(BigDecimal.ZERO)) {
				return this;
			} else if (Grade.isPassing(average)) {
				return CONTINUING;
				// TODO: to grad
			} else {
				return INELIGIBLE;
			}
		}

		@Override
		public int getMinUnits() {
			return 15;
		}

		@Override
		public int getMaxUnits() {
			return 18;
		}

		@Override
		public boolean isEligibleToEnroll() {
			return true;
		}

		@Override
		public boolean mustCheckPrerequisites() {
			return true;
		}

		@Override
		public boolean canTakePrerequisiteSubjects() {
			return true;
		};
	},
	INELIGIBLE() {
		@Override
		public StudentStatus update(BigDecimal average, int unitsLeft, boolean prerequisitesDone) {
			return this;
		}

		@Override
		public int getMinUnits() {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getMaxUnits() {
			return 0;
		}

		@Override
		public boolean isEligibleToEnroll() {
			return false;
		}

		@Override
		public boolean mustCheckPrerequisites() {
			return true;
		}

		@Override
		public boolean canTakePrerequisiteSubjects() {
			return false;
		};
	};

	StudentStatus() {

	}

	public abstract int getMinUnits();

	public abstract int getMaxUnits();

	public abstract boolean isEligibleToEnroll();

	public abstract boolean mustCheckPrerequisites();

	public abstract boolean canTakePrerequisiteSubjects();

	public abstract StudentStatus update(BigDecimal average, int unitsLeft, boolean prerequisitesDone);

}
