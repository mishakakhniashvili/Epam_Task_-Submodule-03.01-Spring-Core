package com.epam.gymcrm.service;

import com.epam.gymcrm.dao.TraineeDao;
import com.epam.gymcrm.dao.TrainerDao;
import com.epam.gymcrm.model.Trainee;
import com.epam.gymcrm.model.Trainer;
import com.epam.gymcrm.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TrainerServiceTest {

    private TrainerService trainerService;
    private TrainerDao trainerDao;
    private TraineeDao traineeDao;

    @BeforeEach
    void setUp() {
        Storage storage = new Storage();

        storage.setTrainees(new HashMap<>());
        storage.setTrainers(new HashMap<>());
        storage.setTrainings(new HashMap<>());
        storage.setTrainingTypes(new HashMap<>());

        trainerDao = new TrainerDao();
        trainerDao.setStorage(storage);

        traineeDao = new TraineeDao();
        traineeDao.setStorage(storage);

        trainerService = new TrainerService();
        trainerService.setTrainerDao(trainerDao);
        trainerService.setTraineeDao(traineeDao);
        trainerService.setUsernameGenerator(new UsernameGenerator());
        trainerService.setPasswordGenerator(new PasswordGenerator());
    }

    @Test
    void shouldCreateTrainerWithGeneratedFields() {
        Trainer trainer = new Trainer(
                null,
                "Mike",
                "Brown",
                null,
                null,
                false,
                "Fitness"
        );

        Trainer createdTrainer = trainerService.create(trainer);

        assertEquals(1L, createdTrainer.getUserId());
        assertEquals("Mike.Brown", createdTrainer.getUsername());
        assertNotNull(createdTrainer.getPassword());
        assertEquals(10, createdTrainer.getPassword().length());
        assertTrue(createdTrainer.isActive());
    }

    @Test
    void shouldGenerateUsernameWithSuffixWhenTraineeAlreadyHasSameUsername() {
        Trainee trainee = new Trainee(
                1L,
                "John",
                "Smith",
                "John.Smith",
                "password123",
                true,
                LocalDate.of(2000, 1, 1),
                "Tbilisi"
        );

        traineeDao.save(trainee);

        Trainer trainer = new Trainer(
                null,
                "John",
                "Smith",
                null,
                null,
                false,
                "Fitness"
        );

        Trainer createdTrainer = trainerService.create(trainer);

        assertEquals("John.Smith1", createdTrainer.getUsername());
    }

    @Test
    void shouldFindTrainerById() {
        Trainer trainer = new Trainer(
                null,
                "Mike",
                "Brown",
                null,
                null,
                false,
                "Fitness"
        );

        Trainer createdTrainer = trainerService.create(trainer);

        Optional<Trainer> foundTrainer = trainerService.findById(createdTrainer.getUserId());

        assertTrue(foundTrainer.isPresent());
        assertEquals("Mike.Brown", foundTrainer.get().getUsername());
    }

    @Test
    void shouldUpdateTrainer() {
        Trainer trainer = new Trainer(
                null,
                "Mike",
                "Brown",
                null,
                null,
                false,
                "Fitness"
        );

        Trainer createdTrainer = trainerService.create(trainer);
        createdTrainer.setSpecialization("Yoga");

        Trainer updatedTrainer = trainerService.update(createdTrainer);

        assertEquals("Yoga", updatedTrainer.getSpecialization());
        assertEquals("Yoga", trainerService.findById(updatedTrainer.getUserId()).get().getSpecialization());
    }
}