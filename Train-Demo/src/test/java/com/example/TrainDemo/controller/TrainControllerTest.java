package com.example.TrainDemo.controller;

import com.example.TrainDemo.model.Train;
import com.example.TrainDemo.request.TrainSearchRequest;
import com.example.TrainDemo.service.TrainSystemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainController.class)
class TrainControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TrainSystemService trainSystemService;
    Train train1;
    Train train2;
    List<Train> trainList = new ArrayList<>();
    TrainSearchRequest trainSearchRequest;
    @BeforeEach
    void setUp() {
        List<String> stations = Arrays.asList(
                "Dharwar Railway Station",
                "Hubli Junction",
                "Davangere Railway Station",
                "Bangalore Yesvantpur Junction",
                "Bangalore City Junction"
        );
        train1 = new Train("SBC VANDE BHARAT Train", "20662", stations);
        train2 = new Train("SBC BGM EXP", "20653", stations);
        trainList.add(train1);
        trainList.add(train2);
        trainSearchRequest = new TrainSearchRequest("Dharwar Railway Station", "Hubli Junction");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testGetTrains() throws Exception{
        when(trainSystemService.getTrain("20662")).thenReturn(train1);
        this.mockMvc.perform(get("/trains-svc/" + "20662")).
                andDo(print()).andExpect(status().isOk());

    }

    @Test
    void testGetAllTrains() throws Exception{
        when(trainSystemService.getAllTrains()).thenReturn(trainList);

        this.mockMvc.perform(get("/trains-svc/all")).
                andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testCreateTrain() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(train1);

        when(trainSystemService.createTrain(train1)).thenReturn("Successfully created train");
        this.mockMvc.perform(post("/trains-svc/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testUpdateTrain() throws  Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(train1);

        when(trainSystemService.updateTrain(train1)).thenReturn("Successfully updated train details");
        this.mockMvc.perform(put("/trains-svc/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteTrain() throws Exception{
        when(trainSystemService.deleteTrain("20662"))
                .thenReturn("Successfully Deleted Train information");
        this.mockMvc.perform(delete("/trains-svc/delete/" + "20662"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testGetAllTrainsBetween() throws Exception {

        TrainSearchRequest searchRequest = new TrainSearchRequest("SourceStation", "DestinationStation");
        List<Train> mockTrains = trainList;
        when(trainSystemService.getAllTrainsBetween(anyString(), anyString())).thenReturn(mockTrains);

        mockMvc.perform(MockMvcRequestBuilders.post("/trains-svc/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllTrainsBetween_ThrowsException() throws Exception {

        TrainSearchRequest searchRequest = new TrainSearchRequest("NonExistentSource", "NonExistentDestination");
        when(trainSystemService.getAllTrainsBetween(anyString(), anyString())).thenReturn(Arrays.asList());

        mockMvc.perform(MockMvcRequestBuilders.post("/trains-svc/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isNotFound());

    }
}