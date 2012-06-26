package schoolsystem.model.schedule;

public enum ScheduleDays {

	MON_AND_THU("Mon/Thu"), TUE_AND_FRI("Tue/Fri"), WED_AND_SAT("Wed/Sat"); 
	
	private String readableForm;
	
	ScheduleDays(String readableForm) {
		this.readableForm = readableForm;
	}
	
	@Override
	public String toString() {
		return this.readableForm;
	}
}
