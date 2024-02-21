package com.example.TrainDemo.repository;

import com.example.TrainDemo.model.Train;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TrainSystemRepositoryTest {
    @Autowired
    private TrainSystemRepository trainSystemRepository;
    Train train;

    @BeforeEach
    void setUp() {
        List<String> stations = Arrays.asList(
                "Dharwar Railway Station",
                "Hubli Junction",
                "Davangere Railway Station",
                "Bangalore Yesvantpur Junction",
                "Bangalore City Junction"
        );
        train = new Train("SBC VANDE BHARAT Train", "20662", stations);
        trainSystemRepository.save(train);
    }
    @AfterEach
    void tearDown() {
        train = null;
        trainSystemRepository.deleteAll();
    }

    @Test
    void testFindByName_Found()
    {
        List<Train> trainList = trainSystemRepository.findByName("SBC VANDE BHARAT Train");
        assertThat(trainList.get(0).getNumber()).isEqualTo(train.getNumber());
        assertThat(trainList.get(0).getName())
                .isEqualTo(train.getName());
    }

    @Test
    void testFindByName_NotFound()
    {
        List<Train> trainList = trainSystemRepository.findByName("SBC Train");
        assertThat(trainList.isEmpty()).isTrue();
    }



}
