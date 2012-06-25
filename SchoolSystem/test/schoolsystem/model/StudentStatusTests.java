package schoolsystem.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import schoolsystem.model.schedule.AcademicTerm;
import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;
import schoolsystem.model.schedule.ScheduleDays;
import schoolsystem.model.schedule.ScheduleTimes;

import java.util.*;

public class StudentStatusTests {
	private Curriculum curriculum;
	private AcademicTerm academicTerm;

	@Before
	public void createFixture() throws Exception {
		academicTerm = AcademicTerm.academicTermAfterCurrent();
		SectionManager.getInstance().reset();
		curriculum = Curriculum.BS_COMPUTER_SCIENCE;
	}

	@Test
	//TODO: Add test that makes sure that all current Subjects have grades before allowing updateStatus
	public void fromNewToContinuing() throws Exception{
		List <Section> sections = getSixSubjectsNoPrerequisites();
		Student newStudent = new Student(1, StudentStatus.NEW, Curriculum.BS_COMPUTER_SCIENCE);
		newStudent.getEnrollmentFormBuilder().addSection(sections.get(0)).addSection(sections.get(1)).addSection(sections.get(2)).addSection(sections.get(3)).addSection(sections.get(4)).addSection(sections.get(5)).enroll();
		assertEquals(1, newStudent.getNumEnrollmentForms());
		newStudent.updateStatus();
		assertEquals(StudentStatus.CONTINUING, newStudent.getStatus());
	}
	
	private List<Section> getSixSubjectsNoPrerequisites() throws Exception{
		List<Section> sectionList = new ArrayList<Section>();
		List<Subject> subjectList = curriculum.getSubjects();
		sectionList.add(createSection("A", subjectList.get(0), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000));
		sectionList.add(createSection("A", subjectList.get(1), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_10000_TO_1130));
		sectionList.add(createSection("A", subjectList.get(2), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1130_TO_1300));
		sectionList.add(createSection("A", subjectList.get(3), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1300_TO_1430));
		sectionList.add(createSection("A", subjectList.get(4), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1430_TO_1600));
		sectionList.add(createSection("A", subjectList.get(5), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1600_TO_1730));
		return sectionList;
	}

	private Section createSection(String sectionName, Subject subject, ScheduleDays scheduleDays, ScheduleTimes scheduleTimes) throws ScheduleConflictException {
		Schedule schedule = new Schedule(academicTerm, scheduleDays, scheduleTimes);
		Teacher teacher = new Teacher(1, "John Doe");
		return new Section(sectionName, subject, schedule, teacher);
	}
}
