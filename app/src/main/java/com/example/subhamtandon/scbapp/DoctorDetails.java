package com.example.subhamtandon.scbapp;

public class DoctorDetails {
    String name;
    String description;

    public DoctorDetails() {
    }

    public DoctorDetails(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
