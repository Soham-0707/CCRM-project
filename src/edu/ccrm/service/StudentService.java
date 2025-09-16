package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.*;

/**
 * Service class for managing students
 * I use a HashMap for fast lookups by registration number
 */
public class StudentService {
    // Using regNo as key because it's unique per student
    private Map<String, Student> registeredStudents = new HashMap<>();

    /**
     * Add a new student to our system
     * Won't add duplicates - I check first to avoid conflicts
     */
    public void addStudent(Student newStudent) {
        if (registeredStudents.containsKey(newStudent.getRegNo())) {
            System.out.println("Hmm, student already exists with RegNo: " + newStudent.getRegNo());
            return;
        }
        
        registeredStudents.put(newStudent.getRegNo(), newStudent);
        System.out.println("Successfully added student: " + newStudent.getRegNo());
    }

    /**
     * Find a student by their registration number
     * Returns null if not found (calling code should check for this)
     */
    public Student getStudent(String registrationNumber) {
        return registeredStudents.get(registrationNumber);
    }

    /**
     * Get all students as a list
     * I create a new ArrayList so external code can't mess with my internal map
     */
    public List<Student> listStudents() {
        return new ArrayList<>(registeredStudents.values());
    }

    /**
     * Deactivate a student (maybe they graduated or transferred)
     * Keeps them in system but marks as inactive
     */
    public void deactivateStudent(String registrationNumber) {
        Student targetStudent = registeredStudents.get(registrationNumber);
        if (targetStudent != null) {
            targetStudent.setActive(false);
            System.out.println("Student deactivated: " + registrationNumber);
        } else {
            System.out.println("No student found with RegNo: " + registrationNumber);
        }
    }
}
