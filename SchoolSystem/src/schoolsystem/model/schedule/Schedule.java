package schoolsystem.model.schedule;

public class Schedule {

	private final ScheduleDays days;
	private final ScheduleTimes timeRange;

	public Schedule(ScheduleDays days, ScheduleTimes timeRange) {
		this.days = days;
		this.timeRange = timeRange;
	}

	public ScheduleDays getDays() {
		return days;
	}

	public ScheduleTimes getTimeRange() {
		return timeRange;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((days == null) ? 0 : days.hashCode());
		result = prime * result + ((timeRange == null) ? 0 : timeRange.hashCode());
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
		Schedule other = (Schedule) obj;
		if (days != other.days)
			return false;
		if (timeRange != other.timeRange)
			return false;
		return true;
	}

}
