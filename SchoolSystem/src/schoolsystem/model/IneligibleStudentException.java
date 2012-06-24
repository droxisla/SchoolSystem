package schoolsystem.model;

@SuppressWarnings("serial")
public class IneligibleStudentException extends Exception {

	private static final long serialVersionUID = 917801818657237814L;

	public IneligibleStudentException() {
		super();
	}

	public IneligibleStudentException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public IneligibleStudentException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public IneligibleStudentException(String arg0) {
		super(arg0);
	}

	public IneligibleStudentException(Throwable arg0) {
		super(arg0);
	}

}
