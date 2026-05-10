package com.epam.gymcrm.service;

import com.epam.gymcrm.dao.TrainingDao;
import com.epam.gymcrm.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {

    private TrainingDao trainingDao;

    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    public Training create(Training training) {
        training.setTrainingId(generateNextId());
        return trainingDao.save(training);
    }

    public Optional<Training> findById(Long trainingId) {
        return trainingDao.findById(trainingId);
    }

    public List<Training> findAll() {
        return trainingDao.findAll();
    }

    private Long generateNextId() {
        return trainingDao.findAll()
                .stream()
                .map(Training::getTrainingId)
                .max(Long::compareTo)
                .orElse(0L) + 1;
    }
}