package schoolsystem.model.schedule;

public class ScheduleConflictException extends Exception {

	public ScheduleConflictException() {
		super();
	}

	public ScheduleConflictException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ScheduleConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScheduleConflictException(String message) {
		super(message);
	}

	public ScheduleConflictException(Throwable cause) {
		super(cause);
	}

}
