package edu.ccrm.domain;

/**
 * Represents one line on a student's transcript
 * Links a course with the marks and grade the student earned
 */
public class TranscriptEntry {
    private String courseCode;
    private int earnedMarks;       // numerical score like 85
    private Grade letterGrade;     // corresponding letter grade like A
    
    // Create a new transcript entry - usually done when grades are posted
    public TranscriptEntry(String courseCode, int earnedMarks, Grade letterGrade) {
        this.courseCode = courseCode;
        this.earnedMarks = earnedMarks;
        this.letterGrade = letterGrade;
    }

    // Simple getters
    public String getCourseCode() { return courseCode; }
    public int getMarks() { return earnedMarks; }
    public Grade getGrade() { return letterGrade; }

    @Override
    public String toString() {
        return String.format("Course: %s, Marks: %d, Grade: %s", 
                           courseCode, earnedMarks, letterGrade);
    }
}
