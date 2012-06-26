package schoolsystem.model;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class CurriculumTest {

	@Test
	public void findAllPrerequisites() {
		Set<Subject> prereqs = new HashSet<Subject>();

		prereqs.add(new Subject("PH 101"));
		prereqs.add(new Subject("PH 102"));
		prereqs.add(new Subject("TH 121"));
		prereqs.add(new Subject("TH 131"));
		prereqs.add(new Subject("FIL 11"));
		prereqs.add(new Subject("FIL 12"));
		prereqs.add(new Subject("PE 1"));
		prereqs.add(new Subject("PE 2"));
		prereqs.add(new Subject("PE 3"));
		prereqs.add(new Subject("MA 18A"));
		prereqs.add(new Subject("MA 18B"));
		prereqs.add(new Subject("AMC 124"));
		prereqs.add(new Subject("NSTP 1"));
		prereqs.add(new Subject("EN 11"));
		prereqs.add(new Subject("LIT 13"));
		prereqs.add(new Subject("HI 16"));
		prereqs.add(new Subject("CS 21A"));
		prereqs.add(new Subject("CS 21B"));
		prereqs.add(new Subject("CS 123"));
		prereqs.add(new Subject("CS 152A"));
		prereqs.add(new Subject("CS 152B"));
		
		Set<Subject> csPrereqs = Curriculum.BS_COMPUTER_SCIENCE.getPrerequisites();
		assertTrue(prereqs.containsAll(csPrereqs));
	}

	@Test
	public void subjectHasPrerequisites() {
		Subject subjectWithNoPrerequisites = new Subject("FLC 1");
		assertTrue(subjectWithNoPrerequisites.getPrerequisites().isEmpty());

		List<Subject> prerequisites = new ArrayList<Subject>();
		prerequisites.add(subjectWithNoPrerequisites);
		Subject subjectWithPrerequisites = new Subject("FLC 2", prerequisites);
		assertTrue(subjectWithPrerequisites.getPrerequisites().size() == 1);
	}

	@Test
	public void findSubject() {
		List<Subject> subjectFound = Curriculum.BS_COMPUTER_SCIENCE.findSubject("PE 4");
		assertTrue(!subjectFound.isEmpty());

		subjectFound = Curriculum.BS_COMPUTER_SCIENCE.findSubject("X" + Math.random());
		assertTrue(subjectFound.isEmpty());
	}

}
