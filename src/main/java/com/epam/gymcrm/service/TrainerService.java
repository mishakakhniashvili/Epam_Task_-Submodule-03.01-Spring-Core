package com.epam.gymcrm.service;

import com.epam.gymcrm.dao.TraineeDao;
import com.epam.gymcrm.dao.TrainerDao;
import com.epam.gymcrm.model.Trainee;
import com.epam.gymcrm.model.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerService.class);
    private TrainerDao trainerDao;
    private TraineeDao traineeDao;
    private UsernameGenerator usernameGenerator;
    private PasswordGenerator passwordGenerator;

    @Autowired
    public void setTrainerDao(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Autowired
    public void setUsernameGenerator(UsernameGenerator usernameGenerator) {
        this.usernameGenerator = usernameGenerator;
    }

    @Autowired
    public void setTraineeDao(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    public Trainer create(Trainer trainer) {
        trainer.setUserId(generateNextId());
        trainer.setUsername(generateUsername(trainer));
        trainer.setPassword(passwordGenerator.generatePassword());
        trainer.setActive(true);

        LOGGER.info("Creating trainer with id={} and username={}", trainer.getUserId(), trainer.getUsername());

        return trainerDao.save(trainer);
    }

    public Trainer update(Trainer trainer) {
        LOGGER.info("Updating trainer with id={}", trainer.getUserId());
        return trainerDao.update(trainer);
    }

    public Optional<Trainer> findById(Long userId) {
        LOGGER.info("Finding trainer with id={}", userId);
        return trainerDao.findById(userId);
    }

    public List<Trainer> findAll() {
        LOGGER.info("finding all trainers");
        return trainerDao.findAll();
    }


    //finds the largest id and creates the next one by adding 1
    private Long generateNextId() {
        return trainerDao.findAll()
                .stream()
                .map(Trainer::getUserId)
                .max(Long::compareTo)
                .orElse(0L) + 1;
    }

    //finds every existing username and generates a new one according to the rules
    private String generateUsername(Trainer trainer) {
        List<String> existingUsernames = trainerDao.findAll()
                .stream()
                .map(Trainer::getUsername)
                .collect(Collectors.toList());
        existingUsernames.addAll(traineeDao.findAll()
                .stream()
                .map(Trainee::getUsername)
                .toList());

        return usernameGenerator.generateUsername(
                trainer.getFirstName(),
                trainer.getLastName(),
                existingUsernames
        );
    }
}