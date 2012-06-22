package schoolsystem.model.section;

public class SectionNameConflictException extends Exception {

	private static final long serialVersionUID = 7585210003264404713L;

	public SectionNameConflictException() {
		super();
	}

	public SectionNameConflictException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SectionNameConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public SectionNameConflictException(String message) {
		super(message);
	}

	public SectionNameConflictException(Throwable cause) {
		super(cause);
	}

	
}
