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
		public boolean canTakeSubjectsWithPrerequisite() {
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
		public boolean canTakeSubjectsWithPrerequisite() {
			return true;
		};
	},
	GRADUATING() {
		@Override
		public StudentStatus update(TermStatus ts) {
			if (ts.getAverage().equals(BigDecimal.ZERO)) {
				return this;
			} else if(0 == ts.getUnitsLeft()) {
				return GRADUATE;
			} else if (!Grade.isPassing(ts.getAverage())) {
				return PROBATIONARY;
			}
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
		public boolean canTakeSubjectsWithPrerequisite() {
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
			return false;
		}

		@Override
		public boolean canTakeSubjectsWithPrerequisite() {
			return false;
		};
	},
	PROBATIONARY() {
		@Override
		public StudentStatus update(TermStatus ts) {
			if (ts.getAverage().equals(BigDecimal.ZERO)) {
				return this;
			} else if (ts.getUnitsLeft() == 0) {
				return GRADUATE;
			} else if (ts.getUnitsLeft() <= UNITS_FOR_GRADUATING && Grade.isPassing(ts.getAverage())) {
				return GRADUATING;
			} else if (Grade.isPassing(ts.getAverage())) {
				return CONTINUING;
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
		public boolean canTakeSubjectsWithPrerequisite() {
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
			return false;
		}

		@Override
		public boolean canTakeSubjectsWithPrerequisite() {
			return false;
		};
	};

	private static final int UNITS_FOR_GRADUATING = 18;

	StudentStatus() {

	}

	public abstract int getMinUnits();

	public abstract int getMaxUnits();

	public abstract boolean isEligibleToEnroll();

	public abstract boolean mustCheckPrerequisites();

	public abstract boolean canTakeSubjectsWithPrerequisite();

	public abstract StudentStatus update(TermStatus ts);

}
