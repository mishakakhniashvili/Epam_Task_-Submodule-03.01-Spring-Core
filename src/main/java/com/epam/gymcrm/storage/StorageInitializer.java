package com.epam.gymcrm.storage;

import com.epam.gymcrm.model.Trainee;
import com.epam.gymcrm.model.Trainer;
import com.epam.gymcrm.model.Training;
import com.epam.gymcrm.model.TrainingType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;

@Component
public class StorageInitializer implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageInitializer.class);

    private final String initFilePath;

    public StorageInitializer(@Value("${storage.init.file}") String initFilePath) {
        this.initFilePath = initFilePath;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Storage storage) {
            loadData(storage);
        }

        return bean;
    }

    private void loadData(Storage storage) {
        LOGGER.info("Loading initial storage data from file: {}", initFilePath);

        ClassPathResource resource = new ClassPathResource(initFilePath);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            reader.lines()
                    .filter(line -> !line.isBlank())
                    .filter(line -> !line.startsWith("#"))
                    .forEach(line -> loadLine(line, storage));

            LOGGER.info("Initial storage data loaded successfully from file: {}", initFilePath);
        } catch (IOException e) {
            LOGGER.error("Could not load initial storage data from file: {}", initFilePath, e);
            throw new IllegalStateException("Could not load initial storage data from file: " + initFilePath, e);
        }
    }

    private void loadLine(String line, Storage storage) {
        String[] values = line.split("\\|");

        switch (values[0]) {
            case "TRAINING_TYPE" -> loadTrainingType(values, storage);
            case "TRAINEE" -> loadTrainee(values, storage);
            case "TRAINER" -> loadTrainer(values, storage);
            case "TRAINING" -> loadTraining(values, storage);
            default -> throw new IllegalArgumentException("Unknown init-data type: " + values[0]);
        }
    }

    private void loadTrainingType(String[] values, Storage storage) {
        TrainingType trainingType = new TrainingType(values[1]);
        storage.getTrainingTypes().put(values[1], trainingType);
    }

    private void loadTrainee(String[] values, Storage storage) {
        Long userId = Long.parseLong(values[1]);
        String firstName = values[2];
        String lastName = values[3];

        Trainee trainee = new Trainee(
                userId,
                firstName,
                lastName,
                firstName + "." + lastName,
                "default123",
                true,
                LocalDate.parse(values[4]),
                values[5]
        );

        storage.getTrainees().put(userId, trainee);
    }

    private void loadTrainer(String[] values, Storage storage) {
        Long userId = Long.parseLong(values[1]);
        String firstName = values[2];
        String lastName = values[3];

        Trainer trainer = new Trainer(
                userId,
                firstName,
                lastName,
                firstName + "." + lastName,
                "default123",
                true,
                values[4]
        );

        storage.getTrainers().put(userId, trainer);
    }

    private void loadTraining(String[] values, Storage storage) {
        Long trainingId = Long.parseLong(values[1]);
        Long traineeId = Long.parseLong(values[2]);
        Long trainerId = Long.parseLong(values[3]);
        String trainingTypeName = values[5];

        TrainingType trainingType = storage.getTrainingTypes()
                .computeIfAbsent(trainingTypeName, TrainingType::new);

        Training training = new Training(
                trainingId,
                traineeId,
                trainerId,
                values[4],
                trainingType,
                LocalDate.parse(values[6]),
                Integer.parseInt(values[7])
        );

        storage.getTrainings().put(trainingId, training);
    }
}