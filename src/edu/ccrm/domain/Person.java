package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Base class for all people in our system - students, instructors, etc.
 * I made this abstract because we never want to create a generic "Person"
 * but always something more specific like a Student.
 */
public abstract class Person {
    protected String personId;
    protected String fullName;
    protected String emailAddress;
    protected boolean isCurrentlyActive;
    protected LocalDate registrationDate;  // when this person was first added to system

    public Person(String personId, String fullName, String emailAddress) {
        this.personId = personId;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.registrationDate = LocalDate.now();
        this.isCurrentlyActive = true;  // new people are active by default
    }

    // Each subclass needs to implement how they want to display their info
    public abstract String getProfile();

    // Basic getters - pretty standard stuff here
    public String getId() { return personId; }
    public String getFullName() { return fullName; }
    public String getEmail() { return emailAddress; }
    public boolean isActive() { return isCurrentlyActive; }
    public LocalDate getCreatedAt() { return registrationDate; }

    // Setters for the fields that can change
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setEmail(String emailAddress) { this.emailAddress = emailAddress; }
    public void setActive(boolean isCurrentlyActive) { this.isCurrentlyActive = isCurrentlyActive; }

    // I overrode toString to give a nice summary of any person
    // This gets called automatically when you print the object
    @Override
    public String toString() {
        return String.format("ID: %s, Name: %s, Email: %s, Active: %b, Since: %s",
            personId, fullName, emailAddress, isCurrentlyActive, registrationDate);
    }
}
