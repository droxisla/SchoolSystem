package schoolsystem.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Subject {

	private static final int NUM_UNITS = 3;

	private final List<Subject> prerequisites;
	private final String name;

	public Subject(String name) {
		this(name, new ArrayList<Subject>());
	}

	public Subject(String name, List<Subject> prerequisites) {
		if (name == null) {
			throw new IllegalArgumentException("Subject name must not be null.");
		}
		if (prerequisites == null) {
			throw new IllegalArgumentException("List of prerequisites must not be null");
		}

		this.name = name;
		this.prerequisites = Collections.unmodifiableList(prerequisites);
	}

	@Override
	public String toString() {
		return name;
	}

	public List<Subject> getPrerequisites() {
		return prerequisites;
	}

	public int getNumberOfUnits() {
		return NUM_UNITS;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
