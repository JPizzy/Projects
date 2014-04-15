package ca.cmpt213.courseplanner.model;

/**
 * author jaspal, jason
 * Represents a section for a course. Distinguished by the section type.
 */
public class Section {
	private String sectionType;
	private int enrollmentTotal;
	private int enrollmentCap;

	public Section(String section, int total, int cap) {
		this.sectionType = section;
		enrollmentTotal = total;
		enrollmentCap = cap;
	}

	public String getSectionType() {
		return sectionType;
	}

	public int getEnrollmentTotal() {
		return enrollmentTotal;
	}

	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	public void addToExistingSection(Section sectionToAdd) {
		this.enrollmentTotal += sectionToAdd.enrollmentTotal;
		this.enrollmentCap += sectionToAdd.enrollmentCap;
	}

	public boolean equals(Section otherSection) {
		return this.sectionType.equals(otherSection.sectionType);
	}

}