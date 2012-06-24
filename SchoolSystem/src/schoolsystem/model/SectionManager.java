package schoolsystem.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import schoolsystem.model.schedule.Schedule;
import schoolsystem.model.schedule.ScheduleConflictException;

public class SectionManager {

	private static final SectionManager INSTANCE = new SectionManager();

	private final Map<Teacher, Map<Schedule, Section>> teacherScheduledSectionMap;
	private final Map<Section, Section> sectionsMap;

	static SectionManager getInstance() {
		return INSTANCE;
	}

	private SectionManager() {
		if (INSTANCE != null) {
			throw new IllegalStateException("Already instantiated");
		}

		teacherScheduledSectionMap = new HashMap<Teacher, Map<Schedule, Section>>();
		sectionsMap = new HashMap<Section, Section>();
	}

	void reset() {
		teacherScheduledSectionMap.clear();
		sectionsMap.clear();
	}

	boolean teacherHasScheduledClass(Teacher teacher, Schedule schedule) {
		Map<Schedule, Section> teacherScheduledSections = teacherScheduledSectionMap.get(teacher);

		if (teacherScheduledSections == null) {
			return false;
		}

		if (teacherScheduledSections.containsKey(schedule)) {
			return true;
		}

		return false;
	}

	boolean teacherHasScheduledClassInSection(Teacher teacher, Schedule schedule, Section section) {
		Map<Schedule, Section> teacherScheduledSections = teacherScheduledSectionMap.get(teacher);

		if (teacherScheduledSections == null) {
			return false;
		}

		Section scheduledSection = teacherScheduledSections.get(schedule);

		if (scheduledSection == null) {
			return false;
		}

		return scheduledSection.equals(section);
	}

	void addSection(Section section) throws ScheduleConflictException {
		Teacher teacher = section.getTeacher();
		Schedule schedule = section.getSchedule();

		if (teacherHasScheduledClass(teacher, schedule)) {
			boolean scheduledClassIsSameSection = teacherHasScheduledClassInSection(teacher, schedule, section);

			if (!scheduledClassIsSameSection) {
				throw new ScheduleConflictException("Schedule conflict for a teacher when adding the section.");
			}
		}

		sectionsMap.put(section, section);
		addTeacherScheduledSection(teacher, schedule, section);
	}

	private void addTeacherScheduledSection(Teacher teacher, Schedule schedule, Section section) {
		Map<Schedule, Section> teacherScheduledSection = teacherScheduledSectionMap.get(teacher);

		if (teacherScheduledSection == null) {
			teacherScheduledSection = new HashMap<Schedule, Section>();
			teacherScheduledSectionMap.put(teacher, teacherScheduledSection);
		}

		teacherScheduledSection.put(schedule, section);
	}

	public boolean hasSection(Section section) {
		return sectionsMap.containsKey(section);
	}

	public Section getSection(Section section) {
		Section storedSection = sectionsMap.get(section);
		if (storedSection == null) {
			return section;
		}
		return storedSection;
	}

}
