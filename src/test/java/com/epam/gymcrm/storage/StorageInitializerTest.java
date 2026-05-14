package com.epam.gymcrm.storage;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StorageInitializerTest {

    @Test
    void shouldLoadInitialDataFromFile() {
        Storage storage = new Storage();

        storage.setTrainees(new HashMap<>());
        storage.setTrainers(new HashMap<>());
        storage.setTrainings(new HashMap<>());
        storage.setTrainingTypes(new HashMap<>());

        StorageInitializer storageInitializer = new StorageInitializer("test-init-data.txt");

        storageInitializer.postProcessAfterInitialization(storage, "storage");

        assertEquals(1, storage.getTrainingTypes().size());
        assertEquals(1, storage.getTrainees().size());
        assertEquals(1, storage.getTrainers().size());
        assertEquals(1, storage.getTrainings().size());

        assertTrue(storage.getTrainingTypes().containsKey("Fitness"));

        assertEquals("John", storage.getTrainees().get(1L).getFirstName());
        assertEquals("Smith", storage.getTrainees().get(1L).getLastName());
        assertEquals("John.Smith", storage.getTrainees().get(1L).getUsername());
        assertEquals(LocalDate.of(2000, 1, 1), storage.getTrainees().get(1L).getDateOfBirth());
        assertEquals("Tbilisi", storage.getTrainees().get(1L).getAddress());

        assertEquals("Mike", storage.getTrainers().get(1L).getFirstName());
        assertEquals("Brown", storage.getTrainers().get(1L).getLastName());
        assertEquals("Mike.Brown", storage.getTrainers().get(1L).getUsername());
        assertEquals("Fitness", storage.getTrainers().get(1L).getSpecialization());

        assertEquals("Morning Training", storage.getTrainings().get(1L).getTrainingName());
        assertEquals(1L, storage.getTrainings().get(1L).getTraineeId());
        assertEquals(1L, storage.getTrainings().get(1L).getTrainerId());
        assertEquals("Fitness", storage.getTrainings().get(1L).getTrainingType().getTrainingTypeName());
        assertEquals(LocalDate.of(2026, 5, 10), storage.getTrainings().get(1L).getTrainingDate());
        assertEquals(60, storage.getTrainings().get(1L).getTrainingDuration());
    }
}