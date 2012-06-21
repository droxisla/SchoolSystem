package schoolsystem.model;

import java.util.*;

public class Curriculum {
	
	private final List<Subject> subjects;
	
	public Curriculum() {
		subjects = new ArrayList<Subject>();
	}
	
	void addSubject(Subject subject) {
		subjects.add(subject);
	}
}
