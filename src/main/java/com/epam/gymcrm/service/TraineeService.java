package com.epam.gymcrm.service;

import com.epam.gymcrm.dao.TraineeDao;
import com.epam.gymcrm.dao.TrainerDao;
import com.epam.gymcrm.model.Trainee;
import com.epam.gymcrm.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TraineeService {

    private TraineeDao traineeDao;
    private TrainerDao trainerDao;
    private UsernameGenerator usernameGenerator;
    private PasswordGenerator passwordGenerator;

    @Autowired
    public void setTraineeDao(TraineeDao traineeDao) {
        this.traineeDao = traineeDao;
    }

    @Autowired
    public void setTrainerDao(TrainerDao trainerDao) {
        this.trainerDao = trainerDao;
    }

    @Autowired
    public void setUsernameGenerator(UsernameGenerator usernameGenerator) {
        this.usernameGenerator = usernameGenerator;
    }

    @Autowired
    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    public Trainee create(Trainee trainee) {
        trainee.setUserId(generateNextId());
        trainee.setUsername(generateUsername(trainee));
        trainee.setPassword(passwordGenerator.generatePassword());
        trainee.setActive(true);
        return traineeDao.save(trainee);
    }

    //finds every existing username and generates a new one according to the rules
    private String generateUsername(Trainee trainee) {
        List<String> existingUsernames = traineeDao.findAll()
                .stream()
                .map(Trainee::getUsername)
                .collect(Collectors.toList());
        existingUsernames.addAll(trainerDao.findAll()
                .stream()
                .map(Trainer::getUsername)
                .toList());
        return usernameGenerator.generateUsername(
                trainee.getFirstName(),
                trainee.getLastName(),
                existingUsernames
        );
    }

    //finds the largest id and creates the next one by adding 1
    private Long generateNextId() {
        return traineeDao.findAll()
                .stream()
                .map(Trainee::getUserId)
                .max(Long::compareTo)
                .orElse(0L)+1;

    }

    public Trainee update(Trainee trainee) {
        return traineeDao.update(trainee);
    }

    public void deleteById(Long userId) {
        traineeDao.deleteById(userId);
    }

    public Optional<Trainee> findById(Long userId) {
        return traineeDao.findById(userId);
    }

    public List<Trainee> findAll() {
        return traineeDao.findAll();
    }
}