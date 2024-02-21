package com.example.TrainDemo.service;

import com.example.TrainDemo.model.Train;

import java.util.List;

public interface TrainSystemService {

    public String createTrain(Train train);
    public String updateTrain(Train train);
    public  String deleteTrain(String id);

    public Train getTrain(String id);

    public List<Train> getAllTrains();
    public List<Train> getAllTrainsBetween(String source, String destination);
}
