package com.example.TrainDemo.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "train_info")
public class Train {

    @Id
    private String number;

    private String name;


    private List<String> stations;
    public Train() {
    }
    public Train( String name, String number, List<String> stations) {

        this.name = name;
        this.number = number;
        this.stations = stations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<String> getStations() {
        return stations;
    }

    public void setStations(List<String> stations) {
        this.stations = stations;
    }
}
