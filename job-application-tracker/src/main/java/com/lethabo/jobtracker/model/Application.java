package com.lethabo.jobtracker.model;

public class Application {

    private Long id;
    private String company;
    private String position;
    private ApplicationStatus status;
    private String location;
    private String notes;

    public Application() {}

    public Application(Long id, String company, String position, ApplicationStatus status,
                       String location, String notes) {
        this.id = id;
        this.company = company;
        this.position = position;
        this.status = status;
        this.location = location;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
