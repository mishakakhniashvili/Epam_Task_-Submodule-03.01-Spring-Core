package com.epam.gymcrm.facade;

import com.epam.gymcrm.model.Trainee;
import com.epam.gymcrm.model.Trainer;
import com.epam.gymcrm.model.Training;
import com.epam.gymcrm.service.TraineeService;
import com.epam.gymcrm.service.TrainerService;
import com.epam.gymcrm.service.TrainingService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GymFacade {

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public GymFacade(TraineeService traineeService,
                     TrainerService trainerService,
                     TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public Trainee createTrainee(Trainee trainee) {
        return traineeService.create(trainee);
    }

    public Trainee updateTrainee(Trainee trainee) {
        return traineeService.update(trainee);
    }

    public void deleteTraineeById(Long userId) {
        traineeService.deleteById(userId);
    }

    public Optional<Trainee> findTraineeById(Long userId) {
        return traineeService.findById(userId);
    }

    public List<Trainee> findAllTrainees() {
        return traineeService.findAll();
    }

    public Trainer createTrainer(Trainer trainer) {
        return trainerService.create(trainer);
    }

    public Trainer updateTrainer(Trainer trainer) {
        return trainerService.update(trainer);
    }

    public Optional<Trainer> findTrainerById(Long userId) {
        return trainerService.findById(userId);
    }

    public List<Trainer> findAllTrainers() {
        return trainerService.findAll();
    }

    public Training createTraining(Training training) {
        return trainingService.create(training);
    }

    public Optional<Training> findTrainingById(Long trainingId) {
        return trainingService.findById(trainingId);
    }

    public List<Training> findAllTrainings() {
        return trainingService.findAll();
    }
}