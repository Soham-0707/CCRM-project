package edu.ccrm.cli;

// Student-related imports
import edu.ccrm.service.StudentService;
import edu.ccrm.domain.Student;

// Course management stuff
import edu.ccrm.service.CourseService;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.service.EnrollmentService;

// Grading and transcript handling
import edu.ccrm.service.GradingService;
import edu.ccrm.domain.TranscriptEntry;

// File I/O operations
import edu.ccrm.io.ImportExportService;
import java.io.IOException;

// Backup utilities
import edu.ccrm.util.BackupUtility;
import java.nio.file.Paths;

import java.util.Scanner;

/**
 * Main CLI interface for the Campus Course & Records Manager
 * Handles all user interactions through a menu-driven system
 */
public class CLIApplication {
    // Service layer dependencies - these handle the business logic
    private StudentService studentManager = new StudentService();
    private CourseService courseManager = new CourseService();
    private EnrollmentService enrollmentHandler = new EnrollmentService();
    private GradingService gradeCalculator = new GradingService();
    private ImportExportService fileOperations = new ImportExportService();

    // Scanner for reading user input - keeping it as instance variable
    private Scanner userInputScanner = new Scanner(System.in);

    /**
     * Main application loop
     * Displays menu and processes user choices until they exit
     */
    public void run() {
        boolean keepRunning = true;
        
        System.out.println("Welcome to the Campus Course & Records Manager!");
        System.out.println("=".repeat(50));  // I like this better than hardcoded equals
        
        while (keepRunning) {
            displayMainMenu();
            
            int userChoice = readUserChoice();
            keepRunning = processMenuSelection(userChoice);
        }
        
        System.out.println("Thanks for using CCRM! Goodbye.");
        userInputScanner.close();  // Clean up resources
    }

    /**
     * Show the main menu options to user
     * I organized these logically - students first, then courses, then operations
     */
    private void displayMainMenu() {
        System.out.println("\n==== Main Menu ====");
        System.out.println("Student Management:");
        System.out.println("  1. Add New Student");
        System.out.println("  2. List All Students");
        System.out.println("  6. View Student Enrollments");
        System.out.println("  8. View Student Transcript");
        
        System.out.println("\nCourse Management:");
        System.out.println("  3. Add New Course");
        System.out.println("  4. List All Courses");
        
        System.out.println("\nAcademic Operations:");
        System.out.println("  5. Enroll Student in Course");
        System.out.println("  7. Assign Grade/Marks");
        
        System.out.println("\nFile Operations:");
        System.out.println("  9. Import Students from CSV");
        System.out.println("  10. Export Students to CSV");
        System.out.println("  11. Import Courses from CSV");
        System.out.println("  12. Export Courses to CSV");
        System.out.println("  13. Backup Data Directory");
        
        System.out.println("\n  0. Exit Application");
        System.out.print("\nPlease select an option: ");
    }
    
    /**
     * Read user's menu choice with some basic error handling
     * I could make this fancier but simple is better here
     */
    private int readUserChoice() {
        try {
            int choice = userInputScanner.nextInt();
            userInputScanner.nextLine(); // consume the leftover newline
            return choice;
        } catch (Exception e) {
            userInputScanner.nextLine(); // clear bad input
            System.out.println("Please enter a valid number.");
            return -1; // invalid choice
        }
    }

    /**
     * Process the user's menu selection
     * Returns false if user wants to exit, true otherwise
     */
    private boolean processMenuSelection(int selectedOption) {
        switch (selectedOption) {
            case 0: 
                return false; // exit the application
            case 1: 
                handleAddNewStudent(); 
                break;
            case 2: 
                displayAllStudents(); 
                break;
            case 3: 
                handleAddNewCourse(); 
                break;
            case 4: 
                displayAllCourses(); 
                break;
            case 5: 
                handleStudentEnrollment(); 
                break;
            case 6: 
                showStudentEnrollmentDetails(); 
                break;
            case 7: 
                handleGradeAssignment(); 
                break;
            case 8: 
                displayStudentTranscript(); 
                break;
            case 9: 
                performStudentImport(); 
                break;
            case 10: 
                performStudentExport(); 
                break;
            case 11: 
                performCourseImport(); 
                break;
            case 12: 
                performCourseExport(); 
                break;
            case 13: 
                performDataBackup(); 
                break;
            default: 
                System.out.println("Hmm, that's not a valid option. Please try again.");
        }
        return true; // continue running
    }

    /**
     * Handle adding a new student to the system
     * Collects all required info from user
     */
    private void handleAddNewStudent() {
        System.out.println("\n--- Add New Student ---");
        
        System.out.print("Enter Student ID: ");
        String studentId = userInputScanner.nextLine();
        
        System.out.print("Enter Registration Number: ");
        String registrationNumber = userInputScanner.nextLine();
        
        System.out.print("Enter Full Name: ");
        String fullName = userInputScanner.nextLine();
        
        System.out.print("Enter Email Address: ");
        String emailAddress = userInputScanner.nextLine();

        // Create the new student object
        Student newStudent = new Student(studentId, registrationNumber, fullName, emailAddress);
        studentManager.addStudent(newStudent);
    }

    /**
     * Display all students currently in the system
     * Nice simple list format
     */
    private void displayAllStudents() {
        System.out.println("\n=== All Students ===");
        
        for (Student currentStudent : studentManager.listStudents()) {
            System.out.println(currentStudent.getProfile());
        }
        
        System.out.println(); // extra space for readability
    }

    /**
     * Handle adding a new course to the system
     * Gathers course details from user input
     */
    private void handleAddNewCourse() {
        System.out.println("\n--- Add New Course ---");
        
        System.out.print("Enter Course Code (e.g., CS101): ");
        String courseCode = userInputScanner.nextLine();
        
        System.out.print("Enter Course Title: ");
        String courseTitle = userInputScanner.nextLine();
        
        System.out.print("Enter Credit Hours (integer): ");
        int creditHours = userInputScanner.nextInt();
        userInputScanner.nextLine(); // consume leftover newline
        
        System.out.print("Enter Instructor ID (optional): ");
        String instructorId = userInputScanner.nextLine();
        
        System.out.print("Enter Semester (SPRING, SUMMER, FALL): ");
        String semesterInput = userInputScanner.nextLine().toUpperCase();
        Semester selectedSemester = Semester.valueOf(semesterInput);
        
        System.out.print("Enter Department Name: ");
        String departmentName = userInputScanner.nextLine();

        Course newCourse = new Course(courseCode, courseTitle, creditHours, 
                                    instructorId, selectedSemester, departmentName);
        courseManager.addCourse(newCourse);
    }

    /**
     * Display all available courses
     */
    private void displayAllCourses() {
        System.out.println("\n=== All Available Courses ===");
        
        for (Course currentCourse : courseManager.listCourses()) {
            System.out.println(currentCourse);
        }
        
        System.out.println();
    }

    /**
     * Handle student enrollment in courses
     * Does validation to make sure both student and course exist
     */
    private void handleStudentEnrollment() {
        System.out.println("\n--- Enroll Student in Course ---");
        
        System.out.print("Enter Student Registration Number: ");
        String studentRegNo = userInputScanner.nextLine();
        
        System.out.print("Enter Course Code: ");
        String targetCourseCode = userInputScanner.nextLine();

        Student selectedStudent = studentManager.getStudent(studentRegNo);
        Course targetCourse = courseManager.listCourses().stream()
                .filter(course -> course.getCode().equals(targetCourseCode))
                .findFirst().orElse(null);

        if (selectedStudent == null) {
            System.out.println("Oops! Student not found: " + studentRegNo);
        } else if (targetCourse == null) {
            System.out.println("Hmm, couldn't find course: " + targetCourseCode);
        } else {
            boolean enrollmentSuccess = enrollmentHandler.enrollStudentInCourse(selectedStudent, targetCourse);
            if (enrollmentSuccess) {
                System.out.println("Great! Enrollment completed successfully.");
            } else {
                System.out.println("Sorry, enrollment failed for some reason.");
            }
        }
    }

    /**
     * Show details of a student's current enrollments
     */
    private void showStudentEnrollmentDetails() {
        System.out.println("\n--- Student Enrollment Details ---");
        
        System.out.print("Enter Student Registration Number: ");
        String studentRegNo = userInputScanner.nextLine();

        Student selectedStudent = studentManager.getStudent(studentRegNo);
        if (selectedStudent == null) {
            System.out.println("Student not found with that registration number.");
        } else {
            System.out.println(selectedStudent.getProfile());
        }
    }

    /**
     * Handle grade assignment for students
     * Gets marks from user and converts to letter grade automatically
     */
    private void handleGradeAssignment() {
        System.out.println("\n--- Assign Grade/Marks ---");
        
        System.out.print("Enter Student Registration Number: ");
        String studentRegNo = userInputScanner.nextLine();
        
        System.out.print("Enter Course Code: ");
        String targetCourseCode = userInputScanner.nextLine();
        
        System.out.print("Enter Marks (0–100): ");
        int earnedMarks = userInputScanner.nextInt(); 
        userInputScanner.nextLine(); // consume newline

        Student targetStudent = studentManager.getStudent(studentRegNo);
        if (targetStudent == null) {
            System.out.println("Sorry, can't find student: " + studentRegNo);
            return;
        }
        
        // Quick check - is student actually enrolled in this course?
        if (!targetStudent.getEnrolledCourses().contains(targetCourseCode)) {
            System.out.println("This student isn't enrolled in that course yet.");
            return;
        }
        
        gradeCalculator.assignMarks(targetStudent, targetCourseCode, earnedMarks);
        System.out.println("Perfect! Marks and grade have been assigned.");
    }

    /**
     * Display a student's complete transcript
     */
    private void displayStudentTranscript() {
        System.out.println("\n--- Student Transcript ---");
        
        System.out.print("Enter Student Registration Number: ");
        String studentRegNo = userInputScanner.nextLine();

        Student selectedStudent = studentManager.getStudent(studentRegNo);
        if (selectedStudent == null) {
            System.out.println("Couldn't find that student in our system.");
        } else {
            System.out.println("=== Transcript for " + selectedStudent.getFullName() + " ===");
            
            for (TranscriptEntry entry : selectedStudent.getTranscript().values()) {
                System.out.println(entry);
            }
            
            double calculatedGPA = gradeCalculator.computeGPA(selectedStudent);
            System.out.printf("Overall GPA: %.2f\n", calculatedGPA);
        }
    }

    /**
     * Import students from a CSV file
     * Handles file reading and error reporting
     */
    private void performStudentImport() {
        System.out.println("\n--- Import Students from CSV ---");
        
        System.out.print("Enter CSV file path: ");
        String csvFilePath = userInputScanner.nextLine();
        
        try {
            int importedCount = fileOperations.importStudents(csvFilePath, studentManager);
            System.out.println("Successfully imported " + importedCount + " students!");
        } catch (IOException e) {
            System.out.println("Oops! Failed to import students: " + e.getMessage());
        }
    }

    /**
     * Export current students to CSV file
     */
    private void performStudentExport() {
        System.out.println("\n--- Export Students to CSV ---");
        
        System.out.print("Enter output file path: ");
        String outputFilePath = userInputScanner.nextLine();
        
        try {
            fileOperations.exportStudents(outputFilePath, studentManager);
            System.out.println("Students exported successfully!");
        } catch (IOException e) {
            System.out.println("Darn! Export failed: " + e.getMessage());
        }
    }
    
    /**
     * Import courses from CSV file
     */
    private void performCourseImport() {
        System.out.println("\n--- Import Courses from CSV ---");
        
        System.out.print("Enter CSV file path for courses: ");
        String csvFilePath = userInputScanner.nextLine();
        
        try {
            int importedCount = fileOperations.importCourses(csvFilePath, courseManager);
            System.out.println("Great! Imported " + importedCount + " courses.");
        } catch (IOException e) {
            System.out.println("Course import failed: " + e.getMessage());
        }
    }

    /**
     * Export courses to CSV file
     */
    private void performCourseExport() {
        System.out.println("\n--- Export Courses to CSV ---");
        
        System.out.print("Enter output file path for courses: ");
        String outputFilePath = userInputScanner.nextLine();
        
        try {
            fileOperations.exportCourses(outputFilePath, courseManager);
            System.out.println("Courses exported successfully!");
        } catch (IOException e) {
            System.out.println("Course export failed: " + e.getMessage());
        }
    }

    /**
     * Create backup of data directory
     * Uses our utility class for recursive directory copying
     */
    private void performDataBackup() {
        System.out.println("\n--- Backup Data Directory ---");
        
        System.out.print("Enter source directory path to backup: ");
        String sourceDirectoryPath = userInputScanner.nextLine();
        
        System.out.print("Enter backup destination directory: ");
        String backupDestinationPath = userInputScanner.nextLine();
        
        try {
            BackupUtility.backupDirectory(Paths.get(sourceDirectoryPath), 
                                        Paths.get(backupDestinationPath));
            System.out.println("Backup completed successfully!");
        } catch (Exception e) {
            System.out.println("Backup operation failed: " + e.getMessage());
        }
    }
}
