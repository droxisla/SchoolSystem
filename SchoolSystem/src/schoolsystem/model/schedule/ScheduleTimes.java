package schoolsystem.model.schedule;

public enum ScheduleTimes {

	FROM_0830_TO_1000("8:30am-10am"), FROM_1000_TO_1130("10am-11:30am"), FROM_1130_TO_1300("11:30am-1pm"), FROM_1300_TO_1430("1pm-2:30om"), FROM_1430_TO_1600("2:30pm-4om"), FROM_1600_TO_1730("4pm-5:30pm");

	private String readableForm;

	ScheduleTimes(String readableForm) {
		this.readableForm = readableForm;
	}

	@Override
	public String toString() {
		return this.readableForm;
	}
}
