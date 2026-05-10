package com.epam.gymcrm.dao;

import com.epam.gymcrm.model.Trainer;
import com.epam.gymcrm.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TrainerDao {

    private Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Trainer save(Trainer trainer) {
        storage.getTrainers().put(trainer.getUserId(), trainer);
        return trainer;
    }

    public Trainer update(Trainer trainer) {
        storage.getTrainers().put(trainer.getUserId(), trainer);
        return trainer;
    }

    public void deleteById(Long userId) {
        storage.getTrainers().remove(userId);
    }

    public Optional<Trainer> findById(Long userId) {
        return Optional.ofNullable(storage.getTrainers().get(userId));
    }

    public List<Trainer> findAll() {
        return new ArrayList<>(storage.getTrainers().values());
    }
}