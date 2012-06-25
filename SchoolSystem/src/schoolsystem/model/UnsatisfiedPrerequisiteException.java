package schoolsystem.model;

public class UnsatisfiedPrerequisiteException extends Exception {

	private static final long serialVersionUID = 7027098038270489778L;

	public UnsatisfiedPrerequisiteException() {
		super();
	}

	public UnsatisfiedPrerequisiteException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnsatisfiedPrerequisiteException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsatisfiedPrerequisiteException(String message) {
		super(message);
	}

	public UnsatisfiedPrerequisiteException(Throwable cause) {
		super(cause);
	}

}
