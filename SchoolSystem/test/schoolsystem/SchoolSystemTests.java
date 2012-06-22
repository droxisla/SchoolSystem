package schoolsystem;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import schoolsystem.model.*;
import schoolsystem.model.schedule.*;


public class SchoolSystemTests {
	
	private Schedule schedule;
	private Subject subject;
	private Teacher teacher;
	private Section section;
	private ClassCard classCard;
	private Curriculum curriculum;
	private Student student;
	private EnrollmentForm enrollmentForm;

	@Before
	public void createFixture() throws Exception {
		schedule = new Schedule(ScheduleDays.MON_AND_THU, ScheduleTimes.FROM_0830_TO_1000);
		teacher = new Teacher(1, "John Doe");
		student = new Student(1, StudentStatus.NEW, curriculum);
	
		subject = new Subject("CS21");
		section = Section.createSection("A", subject, schedule, teacher);
		
		enrollmentForm = new EnrollmentForm(student);
		classCard = new ClassCard(enrollmentForm, section);
	}
	
	@Test
	public void enrollNewStudentInSectionWithNoPrereq() {
		student.beginEnrollment();
		assertEquals(1, student.getNumEnrollmentForms());
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
		assertEquals("1", student.toString());
	}

	@Test
	public void testTeacherToString() {
		assertEquals("John Doe", teacher.toString());
	}

	@Test(expected = SectionFullException.class)
	public void classCardsMustNotExceedForty() throws SectionFullException {
		EnrollmentForm enrollmentForm = new EnrollmentForm(student);
		for (int i = 0; i < 40; i++) {
			new ClassCard(enrollmentForm, section);
		}
	}
}
