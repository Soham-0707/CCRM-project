package edu.ccrm.domain;

/**
 * Grade enum with point values
 * S is the highest (10 points), F is failing (0 points)
 * This follows our university's grading scale
 */
public enum Grade {
    S(10),  // Outstanding - 90-100%
    A(9),   // Excellent - 80-89% 
    B(8),   // Good - 70-79%
    C(7),   // Average - 60-69%
    D(6),   // Below Average - 50-59%
    E(5),   // Poor - 40-49%
    F(0);   // Fail - below 40%

    private final int gradePointValue;
    
    // Constructor for each enum value
    Grade(int gradePointValue) { 
        this.gradePointValue = gradePointValue; 
    }
    
    public int getPoints() { 
        return gradePointValue; 
    }
}
