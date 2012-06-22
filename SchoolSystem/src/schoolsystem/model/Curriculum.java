package schoolsystem.model;

import java.util.*;

public enum Curriculum{
	BS_COMPUTER_SCIENCE() {
		private List<Subject> subjects;
		void initializeSubjects() {
			subjects = Arrays.asList(new Subject("CS 21A"), new Subject("EN 11"), 
					new Subject("LIT 13"), new Subject("FIL 11"), new Subject("MA 18A"), 
					new Subject("MA 18B"), new Subject("PE 1"), new Subject("NSTP 1"), 
					new Subject("CS 21B"), new Subject("EN 12"), new Subject("LIT 14"), 
					new Subject("FIL 12"), new Subject("PE 2"), new Subject("NSTP 2"), 
					new Subject("PSY 101"), new Subject("FLC 1"), new Subject("AMC 124"), 
					new Subject("CS 110"), new Subject("FIL 14"), new Subject("TH 121"), 
					new Subject("PE 3"), new Subject("AMC 125"), new Subject("CS 122"), 
					new Subject("HI 16"), new Subject("SCI 10"), new Subject("PE 4"), 
					new Subject("SA 21"), new Subject("CS 152A"), new Subject("CS 152B"), 
					new Subject("CS 123"), new Subject("CS 165"), new Subject("PH 101"), 
					new Subject("CS 124"), new Subject("CS 162A"), new Subject("CS 162B"), 
					new Subject("PH 102"), new Subject("TH 131"), new Subject("CS 119.1"), 
					new Subject("CS 154"), new Subject("EC 102"), new Subject("HI 166"), 
					new Subject("PH 103"), new Subject("TH 141"), new Subject("CS 130"), 
					new Subject("CS 112"), new Subject("PH 104"), new Subject("POS 100"), 
					new Subject("TH 151"));
		}
		
		public List<Subject> getSubjects() {
			return subjects;
		}
	};
	
	Curriculum() { initializeSubjects(); }
	
	//private List<Subject> subjects;
	abstract void initializeSubjects();
	public abstract List<Subject> getSubjects();
}
