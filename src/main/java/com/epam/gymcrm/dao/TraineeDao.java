package com.epam.gymcrm.dao;

import com.epam.gymcrm.model.Trainee;
import com.epam.gymcrm.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TraineeDao {

    private Storage storage;

    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Trainee save(Trainee trainee) {
        storage.getTrainees().put(trainee.getUserId(), trainee);
        return trainee;
    }

    public Trainee update(Trainee trainee) {
        storage.getTrainees().put(trainee.getUserId(), trainee);
        return trainee;
    }

    public void deleteById(Long userId) {
        storage.getTrainees().remove(userId);
    }

    public Optional<Trainee> findById(Long userId) {
        return Optional.ofNullable(storage.getTrainees().get(userId));
    }

    public List<Trainee> findAll() {
        return new ArrayList<>(storage.getTrainees().values());
    }
}