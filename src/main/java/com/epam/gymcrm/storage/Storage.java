package com.epam.gymcrm.storage;

import com.epam.gymcrm.model.Trainee;
import com.epam.gymcrm.model.Trainer;
import com.epam.gymcrm.model.Training;
import com.epam.gymcrm.model.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Storage {

    private Map<Long, Trainee> trainees;
    private Map<Long, Trainer> trainers;
    private Map<Long, Training> trainings;
    private Map<String, TrainingType> trainingTypes;

    @Autowired
    public void setTrainees(@Qualifier("traineeStorage") Map<Long, Trainee> trainees) {
        this.trainees = trainees;
    }

    @Autowired
    public void setTrainers(@Qualifier("trainerStorage") Map<Long, Trainer> trainers) {
        this.trainers = trainers;
    }

    @Autowired
    public void setTrainings(@Qualifier("trainingStorage") Map<Long, Training> trainings) {
        this.trainings = trainings;
    }

    @Autowired
    public void setTrainingTypes(@Qualifier("trainingTypeStorage") Map<String, TrainingType> trainingTypes) {
        this.trainingTypes = trainingTypes;
    }

    public Map<Long, Trainee> getTrainees() {
        return trainees;
    }

    public Map<Long, Trainer> getTrainers() {
        return trainers;
    }

    public Map<Long, Training> getTrainings() {
        return trainings;
    }

    public Map<String, TrainingType> getTrainingTypes() {
        return trainingTypes;
    }
}