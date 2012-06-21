package schoolsystem;

import static org.junit.Assert.*;

import org.junit.*;

import schoolsystem.model.*;
import schoolsystem.model.Student.Status;

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
	public void createFixture() throws Exception{
		schedule = new Schedule();
		subject = new Subject("CS 21");
		teacher = new Teacher("John Doe");
		section = new Section("A", subject, schedule, teacher);
		classCard = new ClassCard(section);
		curriculum = new Curriculum();
		student = new Student(1, Status.NEW, curriculum);
		enrollmentForm = student.addNewEnrollmentForm();
		teacher.addSection(section);
		section.addClassCard(classCard);
		curriculum.addSubject(subject);
		enrollmentForm.addClassCard(classCard);
	}
	
	@Test
	public void testSubjectToString() {
		assertEquals("CS 21", subject.toString());
	}
	
	@Test
	public void testSectionToString() {
		assertEquals("A", section.toString());
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
	public void testSectionClassCardMax() throws SectionFullException{
		for(int i = 0; i < 40; i++) {
			section.addClassCard(new ClassCard(section));
		}
	}
	
	@Test
	public void testStudentRegister() { 
		//TODO: student.enroll(section);
	}

}
