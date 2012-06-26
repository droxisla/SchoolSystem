package schoolsystem.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import schoolsystem.model.schedule.AcademicTerm;
import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;
import schoolsystem.model.schedule.ScheduleDays;
import schoolsystem.model.schedule.ScheduleTimes;

public class StudentStatusUpdateTest {

	private Curriculum curriculum;
	private AcademicTerm academicTerm;
	private Student newStudent;
	private Student continuingStudent;
	private Student probationaryStudent;

	@Before
	public void createFixture() throws Exception {
		academicTerm = AcademicTerm.academicTermAfterCurrent();
		curriculum = Curriculum.BS_COMPUTER_SCIENCE;
		newStudent = new Student(1, "Juan dela Cruz", StudentStatus.NEW, Curriculum.BS_COMPUTER_SCIENCE);
		continuingStudent = new Student(2, "Juan Cruz", StudentStatus.CONTINUING, Curriculum.BS_COMPUTER_SCIENCE);
		probationaryStudent = new Student(3, "Juan Ruz", StudentStatus.PROBATIONARY, Curriculum.BS_COMPUTER_SCIENCE);
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
	public void fromContinuingToGraduating() throws Exception {
		curriculum = Curriculum.TWELVE_SUBJECTS_SIX_PREREQS;
		continuingStudent = new Student(4, "Juan Cruz", StudentStatus.CONTINUING, curriculum);
		EnrollmentForm ef = enrollStudentInEighteenUnits(continuingStudent);
		setGradesToPassing(ef.getClassCards());
		continuingStudent.updateStatus();
		assertEquals(StudentStatus.GRADUATING, continuingStudent.getStatus());
		academicTerm = academicTerm.getNextAcademicTerm();
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

	@Test
	public void fromProbationaryToGraduate() throws Exception {
		curriculum = Curriculum.SIX_SUBJECTS_NO_PREREQS;
		probationaryStudent = new Student(4, "Juan dela Pena", StudentStatus.PROBATIONARY, curriculum);
		EnrollmentForm ef = enrollStudentInEighteenUnits(probationaryStudent);
		setGradesToPassing(ef.getClassCards());
		probationaryStudent.updateStatus();
		assertEquals(StudentStatus.GRADUATE, probationaryStudent.getStatus());
	}

	@Test
	public void fromProbationaryToGraduating() throws Exception {
		curriculum = Curriculum.SIX_SUBJECTS_NO_PREREQS;
		probationaryStudent = new Student(4, "Juan dela Pena", StudentStatus.PROBATIONARY, curriculum);
		EnrollmentForm ef = enrollStudentInEighteenUnits(probationaryStudent);
		List<ClassCard> classCards = ef.getClassCards();
		for (int i = 0; i < classCards.size(); i++) {
			if (i == 0) {
				classCards.get(i).setGrade(Grade.G5_00);
			} else {
				classCards.get(i).setGrade(Grade.G1_00);
			}
		}
		probationaryStudent.updateStatus();
		assertEquals(StudentStatus.GRADUATING, probationaryStudent.getStatus());
	}

	@Test
	public void fromGraduatingToGraduating() throws Exception {
		curriculum = Curriculum.SIX_SUBJECTS_NO_PREREQS;
		Student graduatingStudent = new Student(4, "Juan dela Pena", StudentStatus.GRADUATING, curriculum);
		EnrollmentForm ef = enrollStudentInEighteenUnits(graduatingStudent);
		List<ClassCard> classCards = ef.getClassCards();
		for (int i = 0; i < classCards.size(); i++) {
			if (i == 0) {
				classCards.get(i).setGrade(Grade.G5_00);
			} else {
				classCards.get(i).setGrade(Grade.G1_00);
			}
		}
		graduatingStudent.updateStatus();
		assertEquals(StudentStatus.GRADUATING, graduatingStudent.getStatus());
	}

	@Test
	public void fromGraduatingToGraduate() throws Exception {
		curriculum = Curriculum.SIX_SUBJECTS_NO_PREREQS;
		Student graduatingStudent = new Student(4, "Juan dela Pena", StudentStatus.GRADUATING, curriculum);
		EnrollmentForm ef = enrollStudentInEighteenUnits(graduatingStudent);
		setGradesToPassing(ef.getClassCards());
		graduatingStudent.updateStatus();
		assertEquals(StudentStatus.GRADUATE, graduatingStudent.getStatus());
	}

	@Test
	public void fromGraduatingtoProbationary() throws Exception {
		curriculum = Curriculum.SIX_SUBJECTS_NO_PREREQS;
		Student graduatingStudent = new Student(4, "Juan dela Pena", StudentStatus.GRADUATING, curriculum);
		EnrollmentForm ef = enrollStudentInEighteenUnits(graduatingStudent);
		setGradesToFailing(ef.getClassCards());
		graduatingStudent.updateStatus();
		assertEquals(StudentStatus.PROBATIONARY, graduatingStudent.getStatus());
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
		List<Section> sections = getSixSubjectsNoPrerequisites();
		EnrollmentForm ef = student.getNewEnrollmentForm();
		ef.addSection(sections.get(0));
		ef.addSection(sections.get(1));
		ef.addSection(sections.get(2));
		ef.addSection(sections.get(3));
		ef.addSection(sections.get(4));
		ef.addSection(sections.get(5));
		ef.submitForEnrollment();
		return ef;
	}

	private EnrollmentForm enrollStudentInEighteenUnits(Student student, List<Section> sections) throws Exception {
		EnrollmentForm ef = student.getNewEnrollmentForm();
		ef.addSection(sections.get(0));
		ef.addSection(sections.get(1));
		ef.addSection(sections.get(2));
		ef.addSection(sections.get(3));
		ef.addSection(sections.get(4));
		ef.addSection(sections.get(5));
		ef.submitForEnrollment();
		return ef;
	}

	private List<Section> getSixSubjectsNoPrerequisites() throws Exception {
		List<Section> sectionList = new ArrayList<Section>();
		List<Subject> subjectList = curriculum.getSubjects();
		int sectionId = 1;

		sectionList.add(createSection(sectionId++, "A", subjectList.get(0), ScheduleDays.MON_AND_THU,
				ScheduleTimes.FROM_0830_TO_1000));
		sectionList.add(createSection(sectionId++, "A", subjectList.get(1), ScheduleDays.MON_AND_THU,
				ScheduleTimes.FROM_1000_TO_1130));
		sectionList.add(createSection(sectionId++, "A", subjectList.get(2), ScheduleDays.MON_AND_THU,
				ScheduleTimes.FROM_1130_TO_1300));
		sectionList.add(createSection(sectionId++, "A", subjectList.get(3), ScheduleDays.MON_AND_THU,
				ScheduleTimes.FROM_1300_TO_1430));
		sectionList.add(createSection(sectionId++, "A", subjectList.get(4), ScheduleDays.MON_AND_THU,
				ScheduleTimes.FROM_1430_TO_1600));
		sectionList.add(createSection(sectionId++, "A", subjectList.get(5), ScheduleDays.MON_AND_THU,
				ScheduleTimes.FROM_1600_TO_1730));
		return sectionList;
	}

	private List<Section> createManySections(List<String> sectionNames) throws Exception {
		List<Section> sectionList = new ArrayList<Section>();
		int sectionId = 1;
		sectionList.add(createSection(sectionId++, "A", curriculum.findSubject(sectionNames.get(0)).get(0),
				ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000));
		sectionList.add(createSection(sectionId++, "A", curriculum.findSubject(sectionNames.get(1)).get(0),
				ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1000_TO_1130));
		sectionList.add(createSection(sectionId++, "A", curriculum.findSubject(sectionNames.get(2)).get(0),
				ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1130_TO_1300));
		sectionList.add(createSection(sectionId++, "A", curriculum.findSubject(sectionNames.get(3)).get(0),
				ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1300_TO_1430));
		sectionList.add(createSection(sectionId++, "A", curriculum.findSubject(sectionNames.get(4)).get(0),
				ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1430_TO_1600));
		sectionList.add(createSection(sectionId++, "A", curriculum.findSubject(sectionNames.get(5)).get(0),
				ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_1600_TO_1730));
		return sectionList;
	}

	private Section createSection(int sectionId, String sectionName, Subject subject, ScheduleDays scheduleDays,
			ScheduleTimes scheduleTimes) throws ScheduleConflictException {
		Schedule schedule = new Schedule(academicTerm, scheduleDays, scheduleTimes);
		Teacher teacher = new Teacher(1, "John Doe");
		return new Section(sectionId, sectionName, subject, schedule, teacher);
	}
}
