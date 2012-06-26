package schoolsystem.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class StudentStatusTest {

	private static final int NO_MIN = 1;
	private static final int NO_MAX = Integer.MAX_VALUE;

	private static final int INELLIGIBLE_MAX = 0;

	@Test
	public void unitsRestriction() {
		assertEquals(15, StudentStatus.NEW.getMinUnits());
		assertEquals(18, StudentStatus.NEW.getMaxUnits());

		assertEquals(18, StudentStatus.CONTINUING.getMinUnits());
		assertEquals(24, StudentStatus.CONTINUING.getMaxUnits());

		assertEquals(NO_MIN, StudentStatus.GRADUATING.getMinUnits());
		assertEquals(NO_MAX, StudentStatus.GRADUATING.getMaxUnits());

		assertEquals(NO_MIN, StudentStatus.GRADUATE.getMinUnits());
		assertEquals(INELLIGIBLE_MAX, StudentStatus.GRADUATE.getMaxUnits());

		assertEquals(15, StudentStatus.PROBATIONARY.getMinUnits());
		assertEquals(18, StudentStatus.PROBATIONARY.getMaxUnits());

		assertEquals(NO_MIN, StudentStatus.INELIGIBLE.getMinUnits());
		assertEquals(INELLIGIBLE_MAX, StudentStatus.INELIGIBLE.getMaxUnits());
	}

	@Test
	public void enrollmentEligibility() {
		assertTrue(StudentStatus.NEW.isEligibleToEnroll());
		assertTrue(StudentStatus.CONTINUING.isEligibleToEnroll());
		assertTrue(StudentStatus.GRADUATING.isEligibleToEnroll());
		assertFalse(StudentStatus.GRADUATE.isEligibleToEnroll());
		assertTrue(StudentStatus.PROBATIONARY.isEligibleToEnroll());
		assertFalse(StudentStatus.INELIGIBLE.isEligibleToEnroll());
	}

	@Test
	public void prerequisites() {
		assertTrue(StudentStatus.NEW.mustCheckPrerequisites());
		assertFalse(StudentStatus.NEW.canTakeSubjectsWithPrerequisite());
		
		assertTrue(StudentStatus.CONTINUING.mustCheckPrerequisites());
		assertTrue(StudentStatus.CONTINUING.canTakeSubjectsWithPrerequisite());
		
		assertFalse(StudentStatus.GRADUATING.mustCheckPrerequisites());
		assertTrue(StudentStatus.GRADUATING.canTakeSubjectsWithPrerequisite());
		
		assertFalse(StudentStatus.GRADUATE.mustCheckPrerequisites());
		assertFalse(StudentStatus.GRADUATE.canTakeSubjectsWithPrerequisite());
		
		assertTrue(StudentStatus.PROBATIONARY.mustCheckPrerequisites());
		assertTrue(StudentStatus.PROBATIONARY.canTakeSubjectsWithPrerequisite());
		
		assertFalse(StudentStatus.INELIGIBLE.mustCheckPrerequisites());
		assertFalse(StudentStatus.INELIGIBLE.canTakeSubjectsWithPrerequisite());
	}
}
