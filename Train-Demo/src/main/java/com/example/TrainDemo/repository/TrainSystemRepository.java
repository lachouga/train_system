package com.example.TrainDemo.repository;

import com.example.TrainDemo.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainSystemRepository extends JpaRepository<Train, String> {
    public List<Train> findByName(String name);
}
