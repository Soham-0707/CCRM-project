package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.Map;

/**
 * Handles all grading-related operations
 * Converts numerical marks to letter grades and calculates GPAs
 */
public class GradingService {
    
    /**
     * Convert numerical marks to letter grade
     * Based on our university's grading scale
     */
    public static Grade calculateGrade(int numericMarks) {
        // I could use a fancy lookup table, but this is clear and readable
        if (numericMarks >= 90) 
            return Grade.S;
        else if (numericMarks >= 80) 
            return Grade.A;
        else if (numericMarks >= 70) 
            return Grade.B;
        else if (numericMarks >= 60) 
            return Grade.C;
        else if (numericMarks >= 50) 
            return Grade.D;
        else if (numericMarks >= 40) 
            return Grade.E;
        else 
            return Grade.F;  // Sorry, you failed
    }

    /**
     * Assign marks to a student for a specific course
     * This updates their transcript automatically
     */
    public void assignMarks(Student targetStudent, String courseCode, int numericMarks) {
        Grade correspondingGrade = calculateGrade(numericMarks);
        targetStudent.addTranscriptEntry(courseCode, numericMarks, correspondingGrade);
    }

    /**
     * Calculate GPA for a student based on their transcript
     * Returns 0.0 if they have no courses (edge case handling)
     */
    public double computeGPA(Student student) {
        Map<String, TranscriptEntry> transcript = student.getTranscript();
        
        if (transcript.isEmpty()) {
            return 0.0;  // No courses = no GPA
        }
        
        double totalGradePoints = 0.0;
        int totalCourses = 0;
        
        // Sum up all grade points
        for (TranscriptEntry entry : transcript.values()) {
            totalGradePoints += entry.getGrade().getPoints();
            totalCourses++;
        }
        
        // Calculate average (avoid division by zero, though we already checked)
        return totalCourses == 0 ? 0.0 : totalGradePoints / totalCourses;
    }
}
