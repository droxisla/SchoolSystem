package schoolsystem.model;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

import schoolsystem.model.schedule.AcademicTerm;
import schoolsystem.model.schedule.ScheduleConflictException;

public class AcademicYearTests {

	@Test
	public void createCurrentAcademicTerm() {
		AcademicTerm academicTerm = AcademicTerm.currentAcademicTerm();

		int year = Calendar.getInstance().get(Calendar.YEAR);
		assertEquals(year, academicTerm.getYear());
	}
	
	@Test
	public void createNextAcademicTerm() {
		AcademicTerm currentAcademicTerm = AcademicTerm.currentAcademicTerm();
		AcademicTerm nextAcademicTerm = AcademicTerm.academicTermAfterCurrent();
		
		assertEquals(currentAcademicTerm.getNextAcademicTerm(), nextAcademicTerm);
	}

	@Test
	public void createAcademicTermFromDate() {
		AcademicTerm term3 = AcademicTerm.fromDate(2012, 1);
		assertEquals(3, term3.getTermNum());

		AcademicTerm term1 = AcademicTerm.fromDate(2012, 6);
		assertEquals(1, term1.getTermNum());

		AcademicTerm term2Dec = AcademicTerm.fromDate(2012, 12);
		assertEquals(2, term2Dec.getTermNum());

		AcademicTerm term2Sept = AcademicTerm.fromDate(2012, 9);
		assertEquals(2, term2Sept.getTermNum());
	}

	@Test(expected = IllegalArgumentException.class)
	public void academicMonthTooLow() {
		AcademicTerm.fromDate(2012, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void academicMonthTooHigh() {
		AcademicTerm.fromDate(2012, 13);
	}

	@Test(expected = IllegalArgumentException.class)
	public void termNumTooLow() {
		AcademicTerm.newInstance(2012, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void termNumTooHigh() {
		AcademicTerm.newInstance(2012, AcademicTerm.MAX_TERM_NUM + 1);
	}

	@Test
	public void nextTerm() {
		int year = 2012;

		AcademicTerm term3 = AcademicTerm.fromDate(year, 1);
		AcademicTerm afterTerm3 = term3.getNextAcademicTerm();
		assertEquals(year, afterTerm3.getYear());
		assertEquals(1, afterTerm3.getTermNum());

		AcademicTerm term1 = AcademicTerm.fromDate(year, 6);
		AcademicTerm afterTerm1 = term1.getNextAcademicTerm();
		assertEquals(year, afterTerm1.getYear());
		assertEquals(2, afterTerm1.getTermNum());

		AcademicTerm term2Sept = AcademicTerm.fromDate(year, 9);
		AcademicTerm afterTerm2Sept = term2Sept.getNextAcademicTerm();
		assertEquals(year + 1, afterTerm2Sept.getYear());
		assertEquals(3, afterTerm2Sept.getTermNum());
	}

}
