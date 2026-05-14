package com.epam.gymcrm.service;

import com.epam.gymcrm.dao.TrainingDao;
import com.epam.gymcrm.model.Training;
import com.epam.gymcrm.model.TrainingType;
import com.epam.gymcrm.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TrainingServiceTest {

    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
        Storage storage = new Storage();

        storage.setTrainees(new HashMap<>());
        storage.setTrainers(new HashMap<>());
        storage.setTrainings(new HashMap<>());
        storage.setTrainingTypes(new HashMap<>());

        TrainingDao trainingDao = new TrainingDao();
        trainingDao.setStorage(storage);

        trainingService = new TrainingService();
        trainingService.setTrainingDao(trainingDao);
    }

    @Test
    void shouldCreateTrainingWithGeneratedId() {
        Training training = new Training(
                null,
                1L,
                1L,
                "Morning Training",
                new TrainingType("Fitness"),
                LocalDate.of(2026, 5, 10),
                60
        );

        Training createdTraining = trainingService.create(training);

        assertEquals(1L, createdTraining.getTrainingId());
        assertEquals(1L, createdTraining.getTraineeId());
        assertEquals(1L, createdTraining.getTrainerId());
        assertEquals("Morning Training", createdTraining.getTrainingName());
        assertEquals("Fitness", createdTraining.getTrainingType().getTrainingTypeName());
        assertEquals(LocalDate.of(2026, 5, 10), createdTraining.getTrainingDate());
        assertEquals(60, createdTraining.getTrainingDuration());
    }

    @Test
    void shouldGenerateNextTrainingId() {
        Training firstTraining = new Training(
                null,
                1L,
                1L,
                "Morning Training",
                new TrainingType("Fitness"),
                LocalDate.of(2026, 5, 10),
                60
        );

        Training secondTraining = new Training(
                null,
                1L,
                1L,
                "Evening Training",
                new TrainingType("Yoga"),
                LocalDate.of(2026, 5, 11),
                45
        );

        Training createdFirstTraining = trainingService.create(firstTraining);
        Training createdSecondTraining = trainingService.create(secondTraining);

        assertEquals(1L, createdFirstTraining.getTrainingId());
        assertEquals(2L, createdSecondTraining.getTrainingId());
    }

    @Test
    void shouldFindTrainingById() {
        Training training = new Training(
                null,
                1L,
                1L,
                "Morning Training",
                new TrainingType("Fitness"),
                LocalDate.of(2026, 5, 10),
                60
        );

        Training createdTraining = trainingService.create(training);

        Optional<Training> foundTraining = trainingService.findById(createdTraining.getTrainingId());

        assertTrue(foundTraining.isPresent());
        assertEquals("Morning Training", foundTraining.get().getTrainingName());
    }

    @Test
    void shouldReturnEmptyOptionalWhenTrainingNotFound() {
        Optional<Training> foundTraining = trainingService.findById(999L);

        assertTrue(foundTraining.isEmpty());
    }
}