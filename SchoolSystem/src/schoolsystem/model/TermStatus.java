package schoolsystem.model;

import java.math.BigDecimal;

public class TermStatus {
	private BigDecimal average;
	private int unitsLeft;
	private boolean prerequisitesDone;

	public TermStatus(BigDecimal average, int unitsLeft,
			boolean prerequisitesDone) {
		this.average = average;
		this.unitsLeft = unitsLeft;
		this.prerequisitesDone = prerequisitesDone;
	}

	public BigDecimal getAverage() {
		return average;
	}

	public int getUnitsLeft() {
		return unitsLeft;
	}

	public boolean isPrerequisitesDone() {
		return prerequisitesDone;
	}
}