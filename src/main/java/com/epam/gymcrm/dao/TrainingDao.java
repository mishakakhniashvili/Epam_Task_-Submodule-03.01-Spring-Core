package com.epam.gymcrm.dao;

import com.epam.gymcrm.model.Training;
import com.epam.gymcrm.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TrainingDao {

    private Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Training save(Training training) {
        storage.getTrainings().put(training.getTrainingId(), training);
        return training;
    }

    public Optional<Training> findById(Long trainingId) {
        return Optional.ofNullable(storage.getTrainings().get(trainingId));
    }

    public List<Training> findAll() {
        return new ArrayList<>(storage.getTrainings().values());
    }
}