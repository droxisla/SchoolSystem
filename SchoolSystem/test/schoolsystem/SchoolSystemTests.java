package schoolsystem;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import schoolsystem.model.ClassCard;
import schoolsystem.model.Curriculum;
import schoolsystem.model.EnrollmentForm;
import schoolsystem.model.Schedule;
import schoolsystem.model.Section;
import schoolsystem.model.SectionFullException;
import schoolsystem.model.Student;
import schoolsystem.model.Student.Status;
import schoolsystem.model.Subject;
import schoolsystem.model.Teacher;

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
		curriculum = new Curriculum();
		
		schedule = new Schedule();
		teacher = new Teacher("John Doe");
		student = new Student(1, Status.NEW, curriculum);
	
		subject = new Subject("CS21", curriculum);
		section = new Section("A", subject, schedule, teacher);
		
		enrollmentForm = new EnrollmentForm(student);
		classCard = new ClassCard(enrollmentForm, section);
	}

	@Test
	public void testTeacherSection() {
		assertEquals(teacher, section.getTeacher());
		assertEquals(true, teacher.hasSection(section));
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
	public void testSectionClassCardMax() throws SectionFullException {
		EnrollmentForm enrollmentForm = new EnrollmentForm(student);
		for (int i = 0; i < 40; i++) {
			new ClassCard(enrollmentForm, section);
		}
	}

	@Test
	public void testStudentRegister() {
		// TODO: student.enroll(section);
	}

}
