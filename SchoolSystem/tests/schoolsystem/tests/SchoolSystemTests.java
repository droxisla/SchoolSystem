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
	public void createFixture() {
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
	
	@Test
	public void testSectionClassCardMax() {
		for(int i = 0; i < 41; i++) {
			//TODO: section.addClassCard(new ClassCard())
		}
	}
	
	@Test
	public void testStudentRegister() { 
		
		//TODO: student.enroll(section);
	}

}
