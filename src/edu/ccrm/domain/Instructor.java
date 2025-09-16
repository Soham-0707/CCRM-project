package edu.ccrm.domain;

/**
 * Instructor class - represents teaching staff
 * Inherits basic person info and adds department affiliation
 */
public class Instructor extends Person {
    private String departmentAffiliation;

    public Instructor(String personId, String fullName, String emailAddress, String departmentAffiliation) {
        super(personId, fullName, emailAddress);
        this.departmentAffiliation = departmentAffiliation;
    }
    
    public String getDepartment() { return departmentAffiliation; }
    public void setDepartment(String departmentAffiliation) { 
        this.departmentAffiliation = departmentAffiliation; 
    }

    @Override
    public String getProfile() {
        return "Instructor [" + super.toString() + ", Dept: " + departmentAffiliation + "]";
    }
}
