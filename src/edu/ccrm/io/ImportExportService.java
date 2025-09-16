package edu.ccrm.io;

import edu.ccrm.domain.*;
import edu.ccrm.service.*;

import java.nio.file.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.*;

/**
 * Service for importing and exporting data via CSV files
 * Handles both students and courses with proper error handling
 */
public class ImportExportService {

    /**
     * Import students from CSV file
     * Expected format: id,regNo,fullName,email
     * Returns number of students successfully imported
     */
    public int importStudents(String csvFilePath, StudentService studentService) throws IOException {
        List<String> dataLines = Files.readAllLines(Paths.get(csvFilePath));
        int successfulImports = 0;
        
        for (String currentLine : dataLines) {
            String[] dataFields = currentLine.strip().split(",");
            
            // Need at least 4 fields for a valid student record
            if (dataFields.length >= 4) {
                try {
                    Student newStudent = new Student(
                        dataFields[0].trim(),  // id
                        dataFields[1].trim(),  // regNo  
                        dataFields[2].trim(),  // fullName
                        dataFields[3].trim()   // email
                    );
                    
                    studentService.addStudent(newStudent);
                    successfulImports++;
                } catch (Exception e) {
                    System.err.println("Skipping invalid student record: " + currentLine + 
                                     " (Error: " + e.getMessage() + ")");
                }
            } else {
                System.err.println("Skipping malformed line (insufficient fields): " + currentLine);
            }
        }
        
        return successfulImports;
    }

    /**
     * Export all current students to CSV file
     * Uses Stream API for clean data transformation
     */
    public void exportStudents(String outputFilePath, StudentService studentService) throws IOException {
        List<String> csvLines = studentService.listStudents()
            .stream()
            .map(student -> String.join(",", 
                student.getId(),
                student.getRegNo(), 
                student.getFullName(),
                student.getEmail()
            ))
            .collect(Collectors.toList());
            
        Files.write(Paths.get(outputFilePath), csvLines);
    }

    /**
     * Import courses from CSV file
     * Expected format: code,title,credits,instructorId,semester,department
     */
    public int importCourses(String csvFilePath, CourseService courseService) throws IOException {
        List<String> dataLines = Files.readAllLines(Paths.get(csvFilePath));
        int successfulImports = 0;
        
        for (String currentLine : dataLines) {
            String[] courseFields = currentLine.strip().split(",");
            
            if (courseFields.length >= 6) {
                try {
                    Course newCourse = new Course(
                        courseFields[0].trim(),  // code
                        courseFields[1].trim(),  // title
                        Integer.parseInt(courseFields[2].trim()), // credits
                        courseFields[3].trim(),  // instructorId
                        Semester.valueOf(courseFields[4].trim().toUpperCase()), // semester
                        courseFields[5].trim()   // department
                    );
                    
                    courseService.addCourse(newCourse);
                    successfulImports++;
                } catch (Exception e) {
                    System.err.println("Skipping invalid course record: " + currentLine + 
                                     " (Error: " + e.getMessage() + ")");
                }
            } else {
                System.err.println("Skipping malformed course line: " + currentLine);
            }
        }
        
        return successfulImports;
    }

    /**
     * Export courses to CSV file
     * Formats all course data into comma-separated values
     */
    public void exportCourses(String outputFilePath, CourseService courseService) throws IOException {
        List<String> csvLines = courseService.listCourses()
            .stream()
            .map(course -> String.join(",",
                course.getCode(),
                course.getTitle(),
                String.valueOf(course.getCredits()),
                course.getInstructorId(),
                course.getSemester().toString(),
                course.getDepartment()
            ))
            .collect(Collectors.toList());
            
        Files.write(Paths.get(outputFilePath), csvLines);
    }
}