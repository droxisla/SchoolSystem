package schoolsystem;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import schoolsystem.model.*;
import schoolsystem.model.schedule.*;
import schoolsystem.model.section.SectionFullException;


public class SchoolSystemTests {
	
	private Schedule schedule;
	private Subject subject;
	private Teacher teacher;
	private Section section;
	private EnrollmentSection enrolledSection;
	private ClassCard classCard;
	private Curriculum curriculum;
	private Student newStudent;
	private EnrollmentForm enrollmentForm;

	@Before
	public void createFixture() throws Exception {
		curriculum = Curriculum.BS_COMPUTER_SCIENCE;
		schedule = new Schedule(ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);
		teacher = new Teacher(1, "John Doe");
		newStudent = new Student(1, StudentStatus.NEW, curriculum);
		
		subject = new Subject("CS21");
		section = subject.createSection("A", schedule, teacher);
		
		enrollmentForm = new EnrollmentForm(newStudent);
		enrolledSection = section.openEnrollmentForNextAcademicTerm();
		classCard = new ClassCard(enrollmentForm, enrolledSection);
	}
	
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
	
	@Test
	public void enrollEligibleStudentInSectionWithNoPrereq() {
		newStudent.beginEnrollment();
		assertEquals(1, newStudent.getNumEnrollmentForms());
	}
	
	@Test(expected = IneligibleStudentException.class)
	public void enrollIneligibleStudent() {
		Student ineligibleStudent = new Student(2, StudentStatus.INELIGIBLE, Curriculum.BS_COMPUTER_SCIENCE);
		ineligibleStudent.beginEnrollment();
	}
	
	@Test(expected = IneligibleStudentException.class)
	public void enrollGraduatedStudent() {
		Student graduatedStudent = new Student(2, StudentStatus.GRADUATE, Curriculum.BS_COMPUTER_SCIENCE);		
		graduatedStudent.beginEnrollment();
	}
	

	@Test
	public void testTeacherSection() {
		assertEquals(teacher, section.getTeacher());
		assertTrue(teacher.hasSection(section));
	}

	@Test
	public void testSubjectToString() {
		assertEquals("CS21", subject.toString());
	}

	@Test
	public void testSectionToString() {
		assertEquals("CS21 A", section.toString());
	}

	@Test
	public void testStudentToString() {
		assertEquals("1", newStudent.toString());
	}

	@Test
	public void testTeacherToString() {
		assertEquals("John Doe", teacher.toString());
	}

	@Test(expected = SectionFullException.class)
	public void classCardsMustNotExceedForty() throws SectionFullException {
		EnrollmentForm enrollmentForm = new EnrollmentForm(newStudent);
		for (int i = 0; i < 40; i++) {
			new ClassCard(enrollmentForm, enrolledSection);
		}
	}
}
