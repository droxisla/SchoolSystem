package schoolsystem.model;

import java.math.BigDecimal;

public class TermStatus {

	private BigDecimal average;
	private int unitsLeft;
	private boolean prerequisitesDone;

	public TermStatus(BigDecimal average, int unitsLeft, boolean prerequisitesDone) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((average == null) ? 0 : average.hashCode());
		result = prime * result + (prerequisitesDone ? 1231 : 1237);
		result = prime * result + unitsLeft;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TermStatus))
			return false;
		TermStatus other = (TermStatus) obj;
		if (average == null) {
			if (other.average != null)
				return false;
		} else if (!average.equals(other.average))
			return false;
		if (prerequisitesDone != other.prerequisitesDone)
			return false;
		if (unitsLeft != other.unitsLeft)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TermStatus [average=" + average + ", unitsLeft=" + unitsLeft + ", prerequisitesDone="
				+ prerequisitesDone + "]";
	}

}