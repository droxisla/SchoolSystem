package schoolsystem.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class StudentStatusTest {

	private static final int NO_MIN = 1;
	private static final int NO_MAX = Integer.MAX_VALUE;

	private static final int INELLIGIBLE_MAX = 0;

	@Test
	public void unitsRestriction() {
		assertEquals(15, StudentStatusType.NEW.getMinUnits());
		assertEquals(18, StudentStatusType.NEW.getMaxUnits());

		assertEquals(18, StudentStatusType.CONTINUING.getMinUnits());
		assertEquals(24, StudentStatusType.CONTINUING.getMaxUnits());

		assertEquals(NO_MIN, StudentStatusType.GRADUATING.getMinUnits());
		assertEquals(NO_MAX, StudentStatusType.GRADUATING.getMaxUnits());

		assertEquals(NO_MIN, StudentStatusType.GRADUATE.getMinUnits());
		assertEquals(INELLIGIBLE_MAX, StudentStatusType.GRADUATE.getMaxUnits());

		assertEquals(15, StudentStatusType.PROBATIONARY.getMinUnits());
		assertEquals(18, StudentStatusType.PROBATIONARY.getMaxUnits());

		assertEquals(NO_MIN, StudentStatusType.INELIGIBLE.getMinUnits());
		assertEquals(INELLIGIBLE_MAX, StudentStatusType.INELIGIBLE.getMaxUnits());
	}

	@Test
	public void enrollmentEligibility() {
		assertTrue(StudentStatusType.NEW.isEligibleToEnroll());
		assertTrue(StudentStatusType.CONTINUING.isEligibleToEnroll());
		assertTrue(StudentStatusType.GRADUATING.isEligibleToEnroll());
		assertFalse(StudentStatusType.GRADUATE.isEligibleToEnroll());
		assertTrue(StudentStatusType.PROBATIONARY.isEligibleToEnroll());
		assertFalse(StudentStatusType.INELIGIBLE.isEligibleToEnroll());
	}

	@Test
	public void prerequisites() {
		assertTrue(StudentStatusType.NEW.mustCheckPrerequisites());
		assertFalse(StudentStatusType.NEW.canTakeSubjectsWithPrerequisite());
		
		assertTrue(StudentStatusType.CONTINUING.mustCheckPrerequisites());
		assertTrue(StudentStatusType.CONTINUING.canTakeSubjectsWithPrerequisite());
		
		assertFalse(StudentStatusType.GRADUATING.mustCheckPrerequisites());
		assertTrue(StudentStatusType.GRADUATING.canTakeSubjectsWithPrerequisite());
		
		assertFalse(StudentStatusType.GRADUATE.mustCheckPrerequisites());
		assertFalse(StudentStatusType.GRADUATE.canTakeSubjectsWithPrerequisite());
		
		assertTrue(StudentStatusType.PROBATIONARY.mustCheckPrerequisites());
		assertTrue(StudentStatusType.PROBATIONARY.canTakeSubjectsWithPrerequisite());
		
		assertFalse(StudentStatusType.INELIGIBLE.mustCheckPrerequisites());
		assertFalse(StudentStatusType.INELIGIBLE.canTakeSubjectsWithPrerequisite());
	}
}
