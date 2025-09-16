package edu.ccrm.service;

import edu.ccrm.domain.Course;
import java.util.*;

/**
 * Manages all course-related operations
 * Keeps track of what courses are available in our system
 */
public class CourseService {
    // Map course code (like "CS101") to Course object
    private Map<String, Course> availableCourses = new HashMap<>();

    /**
     * Register a new course in the system
     * Each course code should be unique
     */
    public void addCourse(Course newCourse) {
        String courseCode = newCourse.getCode();
        
        if (availableCourses.containsKey(courseCode)) {
            System.out.println("Course already exists with code: " + courseCode);
            return;
        }
        
        // All good - add the course
        availableCourses.put(courseCode, newCourse);
        System.out.println("Course successfully added: " + courseCode);
    }

    /**
     * Get all courses as a list
     * Makes it easy to iterate or display all courses
     */
    public List<Course> listCourses() {
        return new ArrayList<>(availableCourses.values());
    }
    
    /**
     * Find a specific course by its code
     * Useful for enrollment operations
     */
    public Course getCourse(String courseCode) {
        return availableCourses.get(courseCode);
    }
    
    /**
     * Get courses for a specific department
     * Nice to have for filtering/reporting
     */
    public List<Course> getCoursesByDepartment(String departmentName) {
        List<Course> departmentCourses = new ArrayList<>();
        
        for (Course course : availableCourses.values()) {
            if (departmentName.equals(course.getDepartment())) {
                departmentCourses.add(course);
            }
        }
        
        return departmentCourses;
    }
}
