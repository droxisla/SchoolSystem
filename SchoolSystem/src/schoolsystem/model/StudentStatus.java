package schoolsystem.model;

import java.math.BigDecimal;

public enum StudentStatus {

	NEW() {
		public StudentStatus update(TermStatus ts) {
			if (ts.getAverage().equals(BigDecimal.ZERO)) {
				return this;
			} else if (Grade.isPassing(ts.getAverage())) {
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
		public StudentStatus update(TermStatus ts) {
			if (ts.getAverage().equals(BigDecimal.ZERO)) {
				return this;
			} else if (!Grade.isPassing(ts.getAverage())) {
				return PROBATIONARY;
			} else if (ts.getUnitsLeft() <= 18 && ts.isPrerequisitesDone()) {
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
		public StudentStatus update(TermStatus ts) {
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
		public StudentStatus update(TermStatus ts) {
			return this;
		}

		@Override
		public int getMinUnits() {
			return 1;
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
	},
	PROBATIONARY() {
		@Override
		public StudentStatus update(TermStatus ts) {
			if (ts.getAverage().equals(BigDecimal.ZERO)) {
				return this;
			} else if (Grade.isPassing(ts.getAverage())) {
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
		public StudentStatus update(TermStatus ts) {
			return this;
		}

		@Override
		public int getMinUnits() {
			return 1;
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

	public abstract StudentStatus update(TermStatus ts);

}
