package edu.ccrm.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Student class extends Person because a student IS-A person
 * They have additional fields like registration number and course enrollments
 */
public class Student extends Person {
    private String registrationNumber;
    private List<String> currentCourseEnrollments; 
    
    // Map course code to transcript entry - makes it easy to look up grades
    private Map<String, TranscriptEntry> academicTranscript = new HashMap<>();
 
    // Constructor - I need all these details to create a valid student
    public Student(String personId, String registrationNumber, String fullName, String emailAddress) {
        super(personId, fullName, emailAddress);
        this.registrationNumber = registrationNumber;
        this.currentCourseEnrollments = new ArrayList<>();  // start with empty course list
    }

    // Getters for student-specific fields
    public String getRegNo() { return registrationNumber; }
    public List<String> getEnrolledCourses() { return currentCourseEnrollments; }
    public Map<String, TranscriptEntry> getTranscript() { return academicTranscript; }

    // Add a grade entry to this student's transcript
    // Usually called from GradingService when grades are assigned
    public void addTranscriptEntry(String courseCode, int marks, Grade grade) {
        academicTranscript.put(courseCode, new TranscriptEntry(courseCode, marks, grade));
    }

    // Enroll in a new course - but only if not already enrolled
    public void enroll(String courseCode) {
        if (!currentCourseEnrollments.contains(courseCode)) {
            currentCourseEnrollments.add(courseCode);
        }
    }
    
    // Remove from enrolled courses
    public void unenroll(String courseCode) {
        currentCourseEnrollments.remove(courseCode);
    }

    @Override
    public String getProfile() {
        return "Student [" + super.toString() + ", RegNo: " + registrationNumber + 
               ", Courses: " + currentCourseEnrollments + "]";
    }
}
