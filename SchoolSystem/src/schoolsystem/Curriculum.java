package schoolsystem;

import java.util.*;

public class Curriculum {
	private List<Subject> subjects;
	
	public Curriculum() {
		subjects = new ArrayList<Subject>();
	}
	
	public void addSubject(Subject subject) {
		subjects.add(subject);
	}
}
