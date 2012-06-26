package schoolsystem.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import schoolsystem.model.schedule.*;

import java.util.*;

public class StudentStatusUpdateTest {
	private Curriculum curriculum;
	private AcademicTerm academicTerm;
	private Student newStudent;
	private Student continuingStudent;
	private Student probationaryStudent;

	@Before
	public void createFixture() throws Exception {
		academicTerm = AcademicTerm.academicTermAfterCurrent();
		SectionManager.getInstance().reset();
		curriculum = Curriculum.BS_COMPUTER_SCIENCE;
		newStudent = new Student(1, StudentStatus.NEW, Curriculum.BS_COMPUTER_SCIENCE);
		continuingStudent = new Student(2, StudentStatus.CONTINUING, Curriculum.BS_COMPUTER_SCIENCE);
		probationaryStudent = new Student(3, StudentStatus.PROBATIONARY, Curriculum.BS_COMPUTER_SCIENCE);
	}
	
	@Test
	public void fromNewToContinuingNoPreviousEnrollment() {
		assertEquals(0, newStudent.getNumEnrollmentForms());
		newStudent.updateStatus();
		assertEquals(StudentStatus.NEW, newStudent.getStatus());
	}

	@Test
	public void fromNewToContinuing() throws Exception {
		EnrollmentForm ef = enrollStudentInEighteenUnits(newStudent);
		assertEquals(1, newStudent.getNumEnrollmentForms());
		
		setGradesToPassing(ef.getClassCards());
		newStudent.updateStatus();
		assertEquals(StudentStatus.CONTINUING, newStudent.getStatus());
	}
	
	@Test
	public void fromNewToProbationary() throws Exception {
		EnrollmentForm ef = enrollStudentInEighteenUnits(newStudent);
		assertEquals(1, newStudent.getNumEnrollmentForms());
		
		setGradesToFailing(ef.getClassCards());
		newStudent.updateStatus();
		assertEquals(StudentStatus.PROBATIONARY, newStudent.getStatus());
	}
	
	@Test
	//TODO: Incomplete
	public void fromContinuingToGraduating() throws Exception {
		EnrollmentForm ef = enrollStudentInEighteenUnits(continuingStudent);		
		setGradesToPassing(ef.getClassCards());
		continuingStudent.updateStatus();
		assertEquals(StudentStatus.CONTINUING, continuingStudent.getStatus());
		SectionManager.getInstance().reset();
		
		List<String> sectionNames = new ArrayList<String>(Arrays.asList("PH 101", "TH 121", "FIL 11", "PE 1", "MA 18A", "MA 18B"));		
		List<Section> sectionList = createManySections(sectionNames);
		ef = enrollStudentInEighteenUnits(continuingStudent, sectionList);		
		setGradesToPassing(ef.getClassCards());
		continuingStudent.updateStatus();
		assertEquals(StudentStatus.CONTINUING, continuingStudent.getStatus());
		SectionManager.getInstance().reset();
		
		sectionNames = new ArrayList<String>(Arrays.asList("PH 102", "TH 131", "AMC 124", "PE 2", "CS 21A", "CS 21B"));		
		sectionList = createManySections(sectionNames);		
		ef = enrollStudentInEighteenUnits(continuingStudent, sectionList);		
		setGradesToPassing(ef.getClassCards());
		continuingStudent.updateStatus();
		assertEquals(StudentStatus.CONTINUING, continuingStudent.getStatus());
		SectionManager.getInstance().reset();
		
		sectionNames = new ArrayList<String>(Arrays.asList("FIL 12", "PE 3", "NSTP 1", "EN 11", "LIT 13", "HI 16"));		
		sectionList = createManySections(sectionNames);		
		ef = enrollStudentInEighteenUnits(continuingStudent, sectionList);		
		setGradesToPassing(ef.getClassCards());
		continuingStudent.updateStatus();
		assertEquals(StudentStatus.CONTINUING, continuingStudent.getStatus());
		SectionManager.getInstance().reset();
		
		sectionNames = new ArrayList<String>(Arrays.asList("CS 123", "CS 152A", "CS 152B", "PH 103", "PH 104", "TH 141"));		
		sectionList = createManySections(sectionNames);		
		ef = enrollStudentInEighteenUnits(continuingStudent, sectionList);		
		setGradesToPassing(ef.getClassCards());
		continuingStudent.updateStatus();
		assertEquals(StudentStatus.CONTINUING, continuingStudent.getStatus());
		SectionManager.getInstance().reset();
		
		sectionNames = new ArrayList<String>(Arrays.asList("TH 151", "FIL 14", "PE 4", "AMC 125", "NSTP 2", "EN 12"));		
		sectionList = createManySections(sectionNames);		
		ef = enrollStudentInEighteenUnits(continuingStudent, sectionList);		
		setGradesToPassing(ef.getClassCards());
		continuingStudent.updateStatus();
		assertEquals(StudentStatus.CONTINUING, continuingStudent.getStatus());
		SectionManager.getInstance().reset();
		
		sectionNames = new ArrayList<String>(Arrays.asList("LIT 14", "HI 166", "CS 110", "CS 122", "CS 165", "CS 119.1"));		
		sectionList = createManySections(sectionNames);		
		ef = enrollStudentInEighteenUnits(continuingStudent, sectionList);		
		setGradesToPassing(ef.getClassCards());
		continuingStudent.updateStatus();
		assertEquals(StudentStatus.GRADUATING, continuingStudent.getStatus());
		SectionManager.getInstance().reset();
		//if remainingUnits <= 18 and all prereqs taken.
	}
	
	@Test
	public void fromContinuingToProbationary() throws Exception {
		EnrollmentForm ef = enrollStudentInEighteenUnits(continuingStudent);
		assertEquals(1, continuingStudent.getNumEnrollmentForms());
		
		setGradesToFailing(ef.getClassCards());
		continuingStudent.updateStatus();
		assertEquals(StudentStatus.PROBATIONARY, continuingStudent.getStatus());
	}
	
	@Test
	public void fromProbationaryToContinuing() throws Exception {
		EnrollmentForm ef = enrollStudentInEighteenUnits(probationaryStudent);
		assertEquals(1, probationaryStudent.getNumEnrollmentForms());
		
		setGradesToPassing(ef.getClassCards());
		probationaryStudent.updateStatus();
		assertEquals(StudentStatus.CONTINUING, probationaryStudent.getStatus());
	}
	
	@Test
	public void fromProbationaryToIneligible() throws Exception {
		EnrollmentForm ef = enrollStudentInEighteenUnits(probationaryStudent);
		assertEquals(1, probationaryStudent.getNumEnrollmentForms());
		
		setGradesToFailing(ef.getClassCards());
		probationaryStudent.updateStatus();
		assertEquals(StudentStatus.INELIGIBLE, probationaryStudent.getStatus());
	}
	
	private void setGradesToPassing(List<ClassCard> classCards) {
		classCards.get(0).setGrade(Grade.G3_00);
		classCards.get(1).setGrade(Grade.G3_00);
		classCards.get(2).setGrade(Grade.G3_00);
		classCards.get(3).setGrade(Grade.G3_00);
		classCards.get(4).setGrade(Grade.G3_00);
		classCards.get(5).setGrade(Grade.G3_00);
	}
	
	private void setGradesToFailing(List<ClassCard> classCards) {
		classCards.get(0).setGrade(Grade.G5_00);
		classCards.get(1).setGrade(Grade.G5_00);
		classCards.get(2).setGrade(Grade.G5_00);
		classCards.get(3).setGrade(Grade.G5_00);
		classCards.get(4).setGrade(Grade.G5_00);
		classCards.get(5).setGrade(Grade.G5_00);
	}
	
	private EnrollmentForm enrollStudentInEighteenUnits(Student student) throws Exception {
		List <Section> sections = getSixSubjectsNoPrerequisites();
		return student.getEnrollmentFormBuilder().addSection(sections.get(0))
												  .addSection(sections.get(1))
												  .addSection(sections.get(2))
												  .addSection(sections.get(3))
												  .addSection(sections.get(4))
												  .addSection(sections.get(5))
												  .enroll();
	}
	
	private EnrollmentForm enrollStudentInEighteenUnits(Student student, List<Section> sections) throws Exception {
		return student.getEnrollmentFormBuilder().addSection(sections.get(0))
												  .addSection(sections.get(1))
												  .addSection(sections.get(2))
												  .addSection(sections.get(3))
												  .addSection(sections.get(4))
												  .addSection(sections.get(5))
												  .enroll();
	}
	
	private List<Section> getSixSubjectsNoPrerequisites() throws Exception{
		List<Section> sectionList = new ArrayList<Section>();
		List<Subject> subjectList = curriculum.getSubjects();
		sectionList.add(createSection("A", subjectList.get(0), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000));
		sectionList.add(createSection("A", subjectList.get(1), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1000_TO_1130));
		sectionList.add(createSection("A", subjectList.get(2), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1130_TO_1300));
		sectionList.add(createSection("A", subjectList.get(3), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1300_TO_1430));
		sectionList.add(createSection("A", subjectList.get(4), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1430_TO_1600));
		sectionList.add(createSection("A", subjectList.get(5), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1600_TO_1730));
		return sectionList;
	}
	
	private List<Section> createManySections(List<String> sectionNames) throws Exception{
		List<Section> sectionList = new ArrayList<Section>();
		sectionList.add(createSection("A", curriculum.findSubject(sectionNames.get(0)).get(0), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000));
		sectionList.add(createSection("A", curriculum.findSubject(sectionNames.get(1)).get(0), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1000_TO_1130));
		sectionList.add(createSection("A", curriculum.findSubject(sectionNames.get(2)).get(0), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1130_TO_1300));
		sectionList.add(createSection("A", curriculum.findSubject(sectionNames.get(3)).get(0), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1300_TO_1430));
		sectionList.add(createSection("A", curriculum.findSubject(sectionNames.get(4)).get(0), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1430_TO_1600));
		sectionList.add(createSection("A", curriculum.findSubject(sectionNames.get(5)).get(0), ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1600_TO_1730));
		return sectionList;
	}

	private Section createSection(String sectionName, Subject subject, ScheduleDays scheduleDays, ScheduleTimes scheduleTimes) throws ScheduleConflictException {
		Schedule schedule = new Schedule(academicTerm, scheduleDays, scheduleTimes);
		Teacher teacher = new Teacher(1, "John Doe");
		return new Section(sectionName, subject, schedule, teacher);
	}
}