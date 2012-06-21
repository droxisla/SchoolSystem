package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import schoolsystem.Student;
import schoolsystem.Student.Status;

public class SchoolSystemTests {

	@Test
	public void testStudentRegister() { 
		Student student = new Student(1, Status.NEW);
		
	}

}
