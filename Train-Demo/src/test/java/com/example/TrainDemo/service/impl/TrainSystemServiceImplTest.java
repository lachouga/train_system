package com.example.TrainDemo.service.impl;

import com.example.TrainDemo.exception.TrainNotFoundException;
import com.example.TrainDemo.model.Train;
import com.example.TrainDemo.repository.TrainSystemRepository;
import com.example.TrainDemo.service.TrainSystemService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TrainSystemServiceImplTest {

    @Mock
    private TrainSystemRepository trainSystemRepository;
    private TrainSystemService trainSystemService;
    AutoCloseable autoCloseable;
    Train train;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        trainSystemService = new TrainSystemServiceImpl(trainSystemRepository);
        List<String> stations = Arrays.asList(
                "Dharwar Railway Station",
                "Hubli Junction",
                "Davangere Railway Station",
                "Bangalore Yesvantpur Junction",
                "Bangalore City Junction"
        );
        train = new Train("SBC VANDE BHARAT Train", "20662", stations);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void testCreateTrain() {
        mock(TrainSystemRepository.class);
        mock(Train.class);

        when(trainSystemRepository.save(train)).thenReturn(train);

        assertThat(trainSystemService.createTrain(train)).isEqualTo("Success");
    }

    @Test
    void testUpdateTrain() {
        mock(TrainSystemRepository.class);
        mock(Train.class);

        when(trainSystemRepository.save(train)).thenReturn(train);

        assertThat(trainSystemService.updateTrain(train)).isEqualTo("Success");
    }

    @Test
    void deleteTrain() {
        mock(TrainSystemRepository.class, Mockito.CALLS_REAL_METHODS);
        mock(Train.class);

        doAnswer(Answers.CALLS_REAL_METHODS).when(trainSystemRepository)
                .deleteById(any());
        AssertionsForClassTypes.assertThat(trainSystemService.deleteTrain("20662")).isEqualTo("Success");

    }

    @Test
    void testGetTrain() {
        mock(TrainSystemRepository.class);
        mock(Train.class);

        when(trainSystemRepository.findById("20662")).thenReturn(Optional.ofNullable(train));

        assertThat(trainSystemService.getTrain("20662").getName()).isEqualTo(train.getName());

    }

    @Test
    void testGetTrain_exception() {
        mock(TrainSystemRepository.class);
        mock(Train.class);

        when(trainSystemRepository.findById("1234")).thenReturn(Optional.empty());

        assertThrows(TrainNotFoundException.class, () -> trainSystemService.getTrain("1234"));
    }



    @Test
    void getAllTrains() {
        mock(TrainSystemRepository.class);
        mock(Train.class);

        when(trainSystemRepository.findAll()).thenReturn(new ArrayList<Train>(
                Collections.singleton(train)
        ));

        assertThat(trainSystemService.getAllTrains().get(0).getName()).isEqualTo(train.getName());
    }

    @Test
    void testGetAllTrainsBetween() {

        List<Train> allTrains = createMockTrainList();
        when(trainSystemRepository.findAll()).thenReturn(allTrains);

        String source = "Hubli Junction";
        String destination = "Bangalore City Junction";

        List<Train> resultTrains = trainSystemService.getAllTrainsBetween(source, destination);

        assertThat(resultTrains).hasSize(2);

        assertThat(resultTrains).extracting(Train::getName)
                .containsExactly("Train1", "Train3");
    }
    @Test
    void testGetAllTrainsBetween_empty(){
        String sourceAndDestination = "Dharwar Railway Station";

        List<Train> resultTrains = trainSystemService.getAllTrainsBetween(sourceAndDestination, sourceAndDestination);

        assertThat(resultTrains).isEmpty();
    }
    private List<Train> createMockTrainList() {
        // Mock data with 3 trains, adjust based on your actual Train class structure
        List<Train> trains = new ArrayList<>();
        trains.add(new Train("Train1", "123", Arrays.asList("Hubli Junction", "Davangere Railway Station", "Bangalore City Junction")));
        trains.add(new Train("Train2", "456", Arrays.asList("Dharwar Railway Station", "Hubli Junction", "Bangalore Yesvantpur Junction")));
        trains.add(new Train("Train3", "789",  Arrays.asList("Dharwar Railway Station", "Hubli Junction", "Bangalore City Junction")));
        return trains;
    }
}