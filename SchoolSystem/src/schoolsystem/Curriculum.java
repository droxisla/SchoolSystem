package schoolsystem;

import java.util.*;

public class Curriculum {
	private List<Subject> subjects;
	
	public void addSubject(Subject subject) {
		subjects = new ArrayList<Subject>();
		subjects.add(subject);
	}
}
