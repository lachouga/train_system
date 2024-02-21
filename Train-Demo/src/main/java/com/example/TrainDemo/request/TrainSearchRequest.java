package com.example.TrainDemo.request;

public class TrainSearchRequest {
    private String source;
    private String destination;

    // Constructors, getters, and setters

    // Default constructor
    public TrainSearchRequest() {
    }

    // Parameterized constructor
    public TrainSearchRequest(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    // Getters and setters
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
