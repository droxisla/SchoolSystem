package schoolsystem.model;

@SuppressWarnings("serial")
public class SectionFullException extends Exception {

	public SectionFullException() {
		super();
	}

	public SectionFullException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SectionFullException(String message, Throwable cause) {
		super(message, cause);
	}

	public SectionFullException(String message) {
		super(message);
	}

	public SectionFullException(Throwable cause) {
		super(cause);
	}

}
