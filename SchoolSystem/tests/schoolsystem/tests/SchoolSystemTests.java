package schoolsystem.tests;

import static org.junit.Assert.*;

import org.junit.*;

import schoolsystem.*;
import schoolsystem.Student.Status;

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
		subject = new Subject();
		teacher = new Teacher();
		section = new Section(subject, schedule, teacher);
		teacher.setSection(section);
		classCard = new ClassCard(section);
		section.addClassCard(classCard);
		curriculum = new Curriculum();
		curriculum.addSubject(subject);
		student = new Student(1, Status.NEW, curriculum);
		enrollmentForm = new EnrollmentForm(student);
		enrollmentForm.addClassCard(classCard);
		student.addEnrollmentForm(enrollmentForm);
	}
	
	@Test(expected = SectionFullException.class)
	public void testSectionClassCardMax() throws SectionFullException{
		for(int i = 0; i < 41; i++) {
			section.addClassCard(new ClassCard(section));
		}
	}
	
	@Test
	public void testStudentRegister() { 
		
		//TODO: student.enroll(section);
	}

}
