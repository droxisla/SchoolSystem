package schoolsystem.model;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public enum Curriculum  {
	EIGHT_SUBJECTS_ONE_PREREQ_SEVENTH() {
		@Override
		List<Subject> initializeSubjects() {
			List<Subject> subjects = new ArrayList<Subject>();
			for(int i = 0; i < 7; i++) {
				subjects.add(new Subject("S"+i));
			}
			subjects.add(new Subject("S"+7, Arrays.asList(subjects.get(6))));
			
			return Collections.unmodifiableList(subjects);
		}
	},
	THIRTEEN_SUBJECTS_SIX_PREREQS() {
		@Override
		List<Subject> initializeSubjects() {
			List<Subject> subjects = new ArrayList<Subject>();
			for(int i = 0; i < 7; i++) {
				subjects.add(new Subject("S"+i));
			}
			for(int i = 7; i < 13; i++) {
				subjects.add(new Subject("S"+i, Arrays.asList(subjects.get(i-7))));
			}
			return Collections.unmodifiableList(subjects);
		}
	},
	TWELVE_SUBJECTS_SIX_PREREQS() {
		@Override
		List<Subject> initializeSubjects() {
			List<Subject> subjects = new ArrayList<Subject>();
			for(int i = 0; i < 6; i++) {
				subjects.add(new Subject("S"+i));
			}
			for(int i = 6; i < 12; i++) {
				subjects.add(new Subject("S"+i, Arrays.asList(subjects.get(i-6))));
			}
			return Collections.unmodifiableList(subjects);
		}
	},
	SIX_SUBJECTS_NO_PREREQS() {
		@Override
		List<Subject> initializeSubjects() {
			List<Subject> subjects = new ArrayList<Subject>();
			for(int i = 0; i < 6; i++) {
				subjects.add(new Subject("S"+i));
			}
			return Collections.unmodifiableList(subjects);
		}
	},
	BS_COMPUTER_SCIENCE() {
		@Override
		List<Subject> initializeSubjects() {
			Subject ph101 = new Subject("PH 101");
			Subject ph102 = new Subject("PH 102", Arrays.asList(ph101));
			Subject ph103 = new Subject("PH 103", Arrays.asList(ph102));
			Subject ph104 = new Subject("PH 104", Arrays.asList(ph102));

			Subject th121 = new Subject("TH 121");
			Subject th131 = new Subject("TH 131", Arrays.asList(th121));
			Subject th141 = new Subject("TH 141", Arrays.asList(th131));
			Subject th151 = new Subject("TH 151", Arrays.asList(th131));

			Subject fil11 = new Subject("FIL 11");
			Subject fil12 = new Subject("FIL 12", Arrays.asList(fil11));
			Subject fil14 = new Subject("FIL 14", Arrays.asList(fil12));

			Subject pe1 = new Subject("PE 1");
			Subject pe2 = new Subject("PE 2", Arrays.asList(pe1));
			Subject pe3 = new Subject("PE 3", Arrays.asList(pe2));
			Subject pe4 = new Subject("PE 4", Arrays.asList(pe3));

			Subject ma18a = new Subject("MA 18A");
			Subject ma18b = new Subject("MA 18B");
			Subject amc124 = new Subject("AMC 124", Arrays.asList(ma18a, ma18b));
			Subject amc125 = new Subject("AMC 125", Arrays.asList(amc124));

			Subject nstp1 = new Subject("NSTP 1");
			Subject nstp2 = new Subject("NSTP 2", Arrays.asList(nstp1));

			Subject en11 = new Subject("EN 11");
			Subject en12 = new Subject("EN 12", Arrays.asList(en11));

			Subject lit13 = new Subject("LIT 13");
			Subject lit14 = new Subject("LIT 14", Arrays.asList(lit13));

			Subject psy101 = new Subject("PSY 101");
			Subject flc1 = new Subject("FLC 1");
			Subject sci10 = new Subject("SCI 10");
			Subject sa21 = new Subject("SA 21");
			Subject ec102 = new Subject("EC 102");
			Subject pos100 = new Subject("POS 100");

			Subject hi16 = new Subject("HI 16");
			Subject hi166 = new Subject("HI 166", Arrays.asList(hi16));

			Subject cs21a = new Subject("CS 21A");
			Subject cs21b = new Subject("CS 21B");

			Subject cs110 = new Subject("CS 110", Arrays.asList(cs21a, cs21b));
			Subject cs122 = new Subject("CS 122", Arrays.asList(cs21a, cs21b));
			Subject cs152a = new Subject("CS 152A", Arrays.asList(cs21a, cs21b));
			Subject cs152b = new Subject("CS 152B", Arrays.asList(cs21a, cs21b));
			Subject cs123 = new Subject("CS 123", Arrays.asList(cs21a, cs21b));
			Subject cs165 = new Subject("CS 165", Arrays.asList(cs21a, cs21b));
			Subject cs119p1 = new Subject("CS 119.1", Arrays.asList(cs21a, cs21b));
			Subject cs154 = new Subject("CS 154", Arrays.asList(cs21a, cs21b));
			Subject cs130 = new Subject("CS 130", Arrays.asList(cs21a, cs21b));
			Subject cs112 = new Subject("CS 112", Arrays.asList(cs21a, cs21b));

			Subject cs124 = new Subject("CS 124", Arrays.asList(cs123));
			Subject cs162a = new Subject("CS 162A", Arrays.asList(cs152a, cs152b));
			Subject cs162b = new Subject("CS 162B", Arrays.asList(cs152a, cs152b));

			List<Subject> subjects = Collections.unmodifiableList(Arrays.asList(psy101, flc1, sci10, sa21, ec102,
					pos100, hi16, hi166, en11, en12, lit13, lit14, nstp1, nstp2, ma18a, ma18b, amc124, amc125, pe1,
					pe2, pe3, pe4, fil11, fil12, fil14, th121, th131, th141, th151, ph101, ph102, ph103, ph104, cs21a,
					cs21b, cs123, cs124, cs152a, cs152b, cs162a, cs162b, cs110, cs122, cs165, cs119p1, cs154, cs130,
					cs112));

			return subjects;
		}

	};

	private List<Subject> allSubjects;
	private Map<String, Subject> subjectMapByName;
	private Set<Subject> prerequisiteSubjects;
	private final int totalUnits;

	Curriculum() {
		allSubjects = initializeSubjects();	
		totalUnits = computeTotalUnits();
		initializeSubjectMapByName();
		createPrerequisiteSet();
	}
	
	private void createPrerequisiteSet() {
		prerequisiteSubjects = new HashSet<Subject>();
		for(Subject s: allSubjects) {
			for(Subject p : s.getPrerequisites()) {
				prerequisiteSubjects.add(p);
			}
		}
	}

	private int computeTotalUnits() {
		int totalUnits = 0;

		for (Subject subject : allSubjects) {
			totalUnits += subject.getNumberOfUnits();
		}

		return totalUnits;
	}

	private void initializeSubjectMapByName() throws AssertionError {
		subjectMapByName = new HashMap<String, Subject>();
		for (Subject subject : allSubjects) {
			String subjectName = subject.getName().toLowerCase();

			if (subjectMapByName.containsKey(subjectName)) {
				throw new AssertionError("Duplicate subject in the same curriculum: " + subject);
			}

			subjectMapByName.put(subjectName, subject);
		}
	}

	abstract List<Subject> initializeSubjects();
	
	public int getTotalUnits() {
		return totalUnits;
	}

	public List<Subject> getSubjects() {
		return allSubjects;
	}
	
	public Set<Subject> getPrerequisites() {
		return prerequisiteSubjects;
	}

	public List<Subject> findSubject(String subjectName) {
		List<Subject> subjectList = new ArrayList<Subject>();

		Subject subject = subjectMapByName.get(subjectName.toLowerCase());
		if (subject != null) {
			subjectList.add(subject);
		}

		return subjectList;
	}
}
