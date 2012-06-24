package schoolsystem.model.schedule;


public class Schedule {

	private final AcademicTerm academicTerm;
	private final ScheduleDays days;
	private final ScheduleTimes timeRange;

	public Schedule(AcademicTerm academicTerm, ScheduleDays days, ScheduleTimes timeRange) {
		this.academicTerm = academicTerm;
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
		result = prime * result + ((academicTerm == null) ? 0 : academicTerm.hashCode());
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
		if (academicTerm == null) {
			if (other.academicTerm != null)
				return false;
		} else if (!academicTerm.equals(other.academicTerm))
			return false;
		if (days != other.days)
			return false;
		if (timeRange != other.timeRange)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Schedule [academicTerm=" + academicTerm + ", days=" + days + ", timeRange=" + timeRange + "]";
	}

	
}
