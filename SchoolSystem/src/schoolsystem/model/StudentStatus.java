package schoolsystem.model;

import schoolsystem.model.schedule.AcademicTerm;

public class StudentStatus {

	private StudentStatusType statusType;
	private AcademicTerm statusTime;

	StudentStatus(StudentStatusType statusType) {
		this.statusType = statusType;
	}

	StudentStatusType getStatusType() {
		return statusType;
	}

	AcademicTerm getStatusTime() {
		return statusTime;
	}

	boolean statusNeedsUpdate(AcademicTerm currentAcademicTerm) {
		assert currentAcademicTerm != null;

		return statusTime == null || statusTime.compareTo(currentAcademicTerm) < 0;
	}

	void updateStatus(AcademicTerm currentAcademicTerm, TermStatus termStatus) {
		assert currentAcademicTerm != null;
		assert termStatus != null;

		this.statusTime = currentAcademicTerm;
		this.statusType = this.statusType.update(termStatus);
	}

}
