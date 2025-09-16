package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Course;

/**
 * Manages student enrollments in courses
 * Basic operations for now, but could add business rules later
 */
public class EnrollmentService {
    
    /**
     * Enroll a student in a course
     * Does basic validation - both student and course must exist
     */
    public boolean enrollStudentInCourse(Student student, Course course) {
        // Basic null checks first
        if (student == null || course == null) {
            System.out.println("Cannot enroll - missing student or course information");
            return false;
        }
        
        // TODO: Could add more business rules here like:
        // - Check if student already enrolled
        // - Verify prerequisites 
        // - Check credit limits
        // - Ensure course has available seats
        
        student.enroll(course.getCode());
        return true;
    }

    /**
     * Remove student from a course
     * Again, basic validation before proceeding
     */
    public boolean unenrollStudentFromCourse(Student student, Course course) {
        if (student == null || course == null) {
            System.out.println("Cannot unenroll - missing student or course information");
            return false;
        }
        
        student.unenroll(course.getCode());
        return true;
    }
    
    /**
     * Check if student is enrolled in a specific course
     * Handy utility method for validation
     */
    public boolean isStudentEnrolledInCourse(Student student, String courseCode) {
        if (student == null || courseCode == null) {
            return false;
        }
        
        return student.getEnrolledCourses().contains(courseCode);
    }
}
