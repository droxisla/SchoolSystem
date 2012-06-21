package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import schoolsystem.*;
import schoolsystem.Student.Status;

public class SchoolSystemTests {

	@Test
	public void testStudentRegister() { 
		Schedule schedule = new Schedule();
		Subject subject = new Subject();
		Teacher teacher = new Teacher();
		Section section = new Section(subject, schedule);
		section.setTeacher(teacher);
		teacher.setSection(section);
		ClassCard classCard = new ClassCard(section);
		section.addClassCard(classCard);
		Curriculum curriculum = new Curriculum();
		curriculum.addSubject(subject);
		Student student = new Student(1, Status.NEW, curriculum);
		//student.enroll(section);
	}

}
