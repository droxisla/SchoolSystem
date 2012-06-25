package schoolsystem.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CurriculumTests {

	@Test
	public void createCurriculum() {
		assertEquals(48, Curriculum.BS_COMPUTER_SCIENCE.getSubjects().size());
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
