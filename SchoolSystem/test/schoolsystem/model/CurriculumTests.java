package schoolsystem.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
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
		assertEquals(Collections.emptyList(), subjectWithNoPrerequisites.getPrerequisites());

		List<Subject> prerequisites = new ArrayList<Subject>();
		prerequisites.add(subjectWithNoPrerequisites);
		Subject subjectWithPrerequisites = new Subject("FLC 2", prerequisites);
		assertEquals(prerequisites, subjectWithPrerequisites.getPrerequisites());
	}

}
