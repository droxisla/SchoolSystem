package schoolsystem.model.schedule;

@SuppressWarnings("serial")
public class ScheduleConflictException extends Exception {

	private static final long serialVersionUID = 7416769492383198079L;

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
