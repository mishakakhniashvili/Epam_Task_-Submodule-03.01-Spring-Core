package com.epam.gymcrm.service;

import com.epam.gymcrm.dao.TraineeDao;
import com.epam.gymcrm.dao.TrainerDao;
import com.epam.gymcrm.model.Trainee;
import com.epam.gymcrm.model.Trainer;
import com.epam.gymcrm.model.Training;
import com.epam.gymcrm.model.TrainingType;
import com.epam.gymcrm.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TraineeServiceTest {

    private TraineeService traineeService;
    private TraineeDao traineeDao;
    private TrainerDao trainerDao;

    @BeforeEach
    void setUp() {
        Storage storage = new Storage();

        storage.setTrainees(new HashMap<>());
        storage.setTrainers(new HashMap<>());
        storage.setTrainings(new HashMap<>());
        storage.setTrainingTypes(new HashMap<>());

        traineeDao = new TraineeDao();
        traineeDao.setStorage(storage);

        trainerDao = new TrainerDao();
        trainerDao.setStorage(storage);

        traineeService = new TraineeService();
        traineeService.setTraineeDao(traineeDao);
        traineeService.setTrainerDao(trainerDao);
        traineeService.setUsernameGenerator(new UsernameGenerator());
        traineeService.setPasswordGenerator(new PasswordGenerator());
    }

    @Test
    void shouldCreateTraineeWithGeneratedFields() {
        Trainee trainee = new Trainee(
                null,
                "John",
                "Smith",
                null,
                null,
                false,
                LocalDate.of(2000, 1, 1),
                "Tbilisi"
        );

        Trainee createdTrainee = traineeService.create(trainee);

        assertEquals(1L, createdTrainee.getUserId());
        assertEquals("John.Smith", createdTrainee.getUsername());
        assertNotNull(createdTrainee.getPassword());
        assertEquals(10, createdTrainee.getPassword().length());
        assertTrue(createdTrainee.isActive());
    }

    @Test
    void shouldGenerateUsernameWithSuffixWhenTrainerAlreadyHasSameUsername() {
        Trainer trainer = new Trainer(
                1L,
                "John",
                "Smith",
                "John.Smith",
                "password123",
                true,
                "Fitness"
        );

        trainerDao.save(trainer);

        Trainee trainee = new Trainee(
                null,
                "John",
                "Smith",
                null,
                null,
                false,
                LocalDate.of(2000, 1, 1),
                "Tbilisi"
        );

        Trainee createdTrainee = traineeService.create(trainee);

        assertEquals("John.Smith1", createdTrainee.getUsername());
    }

    @Test
    void shouldFindTraineeById() {
        Trainee trainee = new Trainee(
                null,
                "John",
                "Smith",
                null,
                null,
                false,
                LocalDate.of(2000, 1, 1),
                "Tbilisi"
        );

        Trainee createdTrainee = traineeService.create(trainee);

        Optional<Trainee> foundTrainee = traineeService.findById(createdTrainee.getUserId());

        assertTrue(foundTrainee.isPresent());
        assertEquals("John.Smith", foundTrainee.get().getUsername());
    }

    @Test
    void shouldDeleteTraineeById() {
        Trainee trainee = new Trainee(
                null,
                "John",
                "Smith",
                null,
                null,
                false,
                LocalDate.of(2000, 1, 1),
                "Tbilisi"
        );

        Trainee createdTrainee = traineeService.create(trainee);

        traineeService.deleteById(createdTrainee.getUserId());

        assertTrue(traineeService.findById(createdTrainee.getUserId()).isEmpty());
    }

    @Test
    void shouldUpdateTrainee() {
        Trainee trainee = new Trainee(
                null,
                "John",
                "Smith",
                null,
                null,
                false,
                LocalDate.of(2000, 1, 1),
                "Tbilisi"
        );

        Trainee createdTrainee = traineeService.create(trainee);
        createdTrainee.setAddress("Batumi");

        Trainee updatedTrainee = traineeService.update(createdTrainee);

        assertEquals("Batumi", updatedTrainee.getAddress());
        assertEquals("Batumi", traineeService.findById(updatedTrainee.getUserId()).get().getAddress());
    }
}