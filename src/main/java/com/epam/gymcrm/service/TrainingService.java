package com.epam.gymcrm.service;

import com.epam.gymcrm.dao.TrainingDao;
import com.epam.gymcrm.model.Training;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingService.class);
    private TrainingDao trainingDao;

    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    public Training create(Training training) {
        training.setTrainingId(generateNextId());

        LOGGER.info("Creating training with id={} and name={}", training.getTrainingId(), training.getTrainingName());

        return trainingDao.save(training);
    }

    public Optional<Training> findById(Long trainingId) {
        LOGGER.info("finding training with id={}", trainingId) ;
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