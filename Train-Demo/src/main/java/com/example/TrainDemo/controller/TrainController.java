package com.example.TrainDemo.controller;

import com.example.TrainDemo.exception.TrainNotFoundException;
import com.example.TrainDemo.model.Train;
import com.example.TrainDemo.request.TrainSearchRequest;
import com.example.TrainDemo.response.ResponseHandler;
import com.example.TrainDemo.service.TrainSystemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trains-svc")
public class TrainController {

    TrainSystemService trainSystemService;

    public TrainController(TrainSystemService trainSystemService) {
        this.trainSystemService = trainSystemService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTrains(@PathVariable("id") String id){
       return ResponseHandler.responseBuilder(HttpStatus.OK,"Requested train details are given",
                 trainSystemService.getTrain(id));

    }

    @GetMapping("/all")
    public List<Train> getAllTrains(){
        return trainSystemService.getAllTrains();
    }

    @PostMapping("/add")
    public String createTrain(@RequestBody Train train){
        trainSystemService.createTrain(train);
        return "Successfully created train";
    }

    @PutMapping("/update")
    public String updateTrain(@RequestBody Train train){
        trainSystemService.updateTrain(train);
        return "Successfully updated train details";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteTrain(@PathVariable("id") String id){
        trainSystemService.deleteTrain(id);
        return "Successfully Deleted Train information";
    }

    @PostMapping ("/find")
    public ResponseEntity<Object> getAllTrainsBetween(@RequestBody TrainSearchRequest searchRequest){
        List<Train> trains = trainSystemService.getAllTrainsBetween(searchRequest.getSource(), searchRequest.getDestination());
        if(trains.isEmpty()){
            throw new TrainNotFoundException("Trains n ot found between given source and destination");
        }
        return ResponseHandler.responseBuilder(HttpStatus.OK,"Trains found between given source and destination",
                trains);
    }
}
