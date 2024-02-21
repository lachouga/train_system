package com.example.TrainDemo.service.impl;

import com.example.TrainDemo.exception.TrainNotFoundException;
import com.example.TrainDemo.model.Train;
import com.example.TrainDemo.repository.TrainSystemRepository;
import com.example.TrainDemo.service.TrainSystemService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TrainSystemServiceImpl implements TrainSystemService {

    TrainSystemRepository trainSystemRepository;

    public TrainSystemServiceImpl(TrainSystemRepository trainSystemRepository) {
        this.trainSystemRepository = trainSystemRepository;
    }

    @Override
    public String createTrain(Train train) {
        trainSystemRepository.save(train);
        return "Success";
    }

    @Override
    public String updateTrain(Train train) {
        trainSystemRepository.save(train);
        return "Success";
    }

    @Override
    public String deleteTrain(String id) {
        trainSystemRepository.deleteById(id);
        return "Success";
    }

    @Override
    public Train getTrain(String id) {
        if(trainSystemRepository.findById(id).isEmpty()){
            throw new TrainNotFoundException("Requested train is not found");
        }
        return trainSystemRepository.findById(id).get();
    }

    @Override
    public List<Train> getAllTrains() {
        return trainSystemRepository.findAll();
    }



    @Override
    public List<Train> getAllTrainsBetween(String source, String destination) {
        String src = source.replaceAll("\\s+", " ").trim();
        String des = destination.replaceAll("\\s+", " ").trim();
        if(source.equals(destination)){
            return new ArrayList<>();
        }
        List<Train> trains =  getAllTrains();
        List<Train> resultTrains = new ArrayList<>();
        for(Train train: trains){
            List<String> stations = train.getStations();
            if (stations.contains(src) && stations.contains(des)) {
                resultTrains.add(train);
            }
        }
        return resultTrains;
    }
}
