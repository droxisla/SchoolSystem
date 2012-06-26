package schoolsystem.model;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class CurriculumTests {
	
	@Test
	public void findAllPrerequisites() {
		Set<Subject> prereqs = new HashSet<Subject>(Arrays.asList(new Subject("PH 101"), new Subject("PH 102"),
																  new Subject("TH 121"), new Subject("TH 131"),
																  new Subject("FIL 11"), new Subject("FIL 12"),
																  new Subject("PE 1"), new Subject("PE 2"),
																  new Subject("PE 3"), new Subject("MA 18A"),
																  new Subject("MA 18B"), new Subject("AMC 124"),
																  new Subject("NSTP 1"), new Subject("EN 11"),
																  new Subject("LIT 13"), new Subject("HI 16"),
																  new Subject("CS 21A"), new Subject("CS 21B"),
																  new Subject("CS 123"), new Subject("CS 152A"),
																  new Subject("CS 152B")));
		
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
