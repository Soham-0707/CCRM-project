package edu.ccrm.domain;

/**
 * Course represents an academic course offering
 * Things like "CS101 - Introduction to Programming"
 */
public class Course {
    private String courseCode;      // like "CS101" 
    private String courseTitle;     // like "Introduction to Programming"
    private int creditHours;        // usually 3 or 4 credits
    private String assignedInstructorId;
    private Semester offeringSemester;
    private String departmentName;

    // Constructor - need all these fields to make a proper course
    public Course(String courseCode, String courseTitle, int creditHours, 
                  String assignedInstructorId, Semester offeringSemester, String departmentName) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.creditHours = creditHours;
        this.assignedInstructorId = assignedInstructorId;
        this.offeringSemester = offeringSemester;
        this.departmentName = departmentName;
    }

    // Standard getters - course code shouldn't change but other things might
    public String getCode() { return courseCode; }
    public String getTitle() { return courseTitle; }
    public int getCredits() { return creditHours; }
    public String getInstructorId() { return assignedInstructorId; }
    public Semester getSemester() { return offeringSemester; }
    public String getDepartment() { return departmentName; }

    // Setters for things that can be modified after creation
    public void setTitle(String courseTitle) { this.courseTitle = courseTitle; }
    public void setCredits(int creditHours) { this.creditHours = creditHours; }
    public void setInstructorId(String assignedInstructorId) { this.assignedInstructorId = assignedInstructorId; }
    public void setSemester(Semester offeringSemester) { this.offeringSemester = offeringSemester; }
    public void setDepartment(String departmentName) { this.departmentName = departmentName; }

    @Override
    public String toString() {
        return String.format("Course [%s: %s, Credits: %d, Instructor: %s, Semester: %s, Dept: %s]",
            courseCode, courseTitle, creditHours, assignedInstructorId, offeringSemester, departmentName);
    }
}
