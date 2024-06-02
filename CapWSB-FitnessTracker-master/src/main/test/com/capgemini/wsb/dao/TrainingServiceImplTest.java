package com.capgemini.wsb.dao;

import com.capgemini.wsb.fitnesstracker.training.internal.*;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TestTraining;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TrainingServiceImplTest {

    private TrainingServiceImpl trainingService;
    private TrainingRepository trainingRepository;
    private UserRepository userRepository;
    private TrainingMapper trainingMapper;

    @BeforeEach
    void setUp() {
       trainingRepository = new TrainingRepository() {
           @Override
           public List<Training> findAll(Sort sort) {
               return List.of();
           }

           @Override
           public Page<Training> findAll(Pageable pageable) {
               return null;
           }

           @Override
           public <S extends Training> S save(S entity) {
               return null;
           }

           @Override
           public <S extends Training> List<S> saveAll(Iterable<S> entities) {
               return List.of();
           }

           @Override
           public Optional<Training> findById(Long aLong) {
               return Optional.empty();
           }

           @Override
           public boolean existsById(Long aLong) {
               return false;
           }

           @Override
           public List<Training> findAll() {
               return List.of();
           }

           @Override
           public List<Training> findAllById(Iterable<Long> longs) {
               return List.of();
           }

           @Override
           public long count() {
               return 0;
           }

           @Override
           public void deleteById(Long aLong) {

           }

           @Override
           public void delete(Training entity) {

           }

           @Override
           public void deleteAllById(Iterable<? extends Long> longs) {

           }

           @Override
           public void deleteAll(Iterable<? extends Training> entities) {

           }

           @Override
           public void deleteAll() {

           }

           @Override
           public void flush() {

           }

           @Override
           public <S extends Training> S saveAndFlush(S entity) {
               return null;
           }

           @Override
           public <S extends Training> List<S> saveAllAndFlush(Iterable<S> entities) {
               return List.of();
           }

           @Override
           public void deleteAllInBatch(Iterable<Training> entities) {

           }

           @Override
           public void deleteAllByIdInBatch(Iterable<Long> longs) {

           }

           @Override
           public void deleteAllInBatch() {

           }

           @Override
           public Training getOne(Long aLong) {
               return null;
           }

           @Override
           public Training getById(Long aLong) {
               return null;
           }

           @Override
           public Training getReferenceById(Long aLong) {
               return null;
           }

           @Override
           public <S extends Training> Optional<S> findOne(Example<S> example) {
               return Optional.empty();
           }

           @Override
           public <S extends Training> List<S> findAll(Example<S> example) {
               return List.of();
           }

           @Override
           public <S extends Training> List<S> findAll(Example<S> example, Sort sort) {
               return List.of();
           }

           @Override
           public <S extends Training> Page<S> findAll(Example<S> example, Pageable pageable) {
               return null;
           }

           @Override
           public <S extends Training> long count(Example<S> example) {
               return 0;
           }

           @Override
           public <S extends Training> boolean exists(Example<S> example) {
               return false;
           }

           @Override
           public <S extends Training, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
               return null;
           }

           @Override
           public List<Training> findByActivityType(ActivityType activityType) {
               return List.of();
           }

           @Override
           public Collection<Training> findByDateBefore(LocalDate parsedDate) {
               return List.of();
           }

           @Override
           public Collection<Training> findByUserId(Long userId) {
               return List.of();
           }
       };
        trainingMapper = new TrainingMapper();
        trainingService = new TrainingServiceImpl(trainingRepository, userRepository, trainingMapper);
    }

    @Test
    void testAddTraining() {
        // Given
        Training training = new TestTraining();
        training.setActivityType("Cycling");

        // When
        Training savedTraining = trainingService.addTraining(training);

        // Then
        assertNotNull(savedTraining);
        assertNotNull(savedTraining.getId());
        assertEquals("Cycling", savedTraining.getActivityType());
    }

    @Test
    void testGetAllTrainings() {
        // Given
        Training training1 = new TestTraining();
        training1.setActivityType("Running");
        Training training2 = new TestTraining();
        training2.setActivityType("Swimming");

        trainingRepository.save(training1);
        trainingRepository.save(training2);

        // When
        List<TrainingDto> trainings = trainingService.getAllTrainings();

        // Then
        assertNotNull(trainings);
        assertEquals(0, trainings.size());
    }

    @Test
    void testDeleteTraining() {
        // Given
        Training training = new TestTraining();
        training.setActivityType("Swimming");
        Training savedTraining = trainingRepository.save(training);

        // When
        trainingService.deleteTraining(savedTraining.getId());

        // Then
        assertFalse(trainingRepository.findById(savedTraining.getId()).isPresent());
    }

    @Test
    void testUpdateTraining() {
        // Given
        Training training = new TestTraining();
        training.setActivityType("Running");
        Training savedTraining = trainingRepository.save(training);

        // When
        savedTraining.setActivityType("Cycling");
        Training updatedTraining = trainingService.updateTraining(savedTraining);

        // Then
        assertNotNull(updatedTraining);
        assertEquals("Cycling", updatedTraining.getActivityType());
    }

    @Test
    void testGetTrainingsByActivityType() {
        // Given
        Training training1 = new TestTraining();
        training1.setActivityType("Running");
        Training training2 = new TestTraining();
        training2.setActivityType("Running");
        Training training3 = new TestTraining();
        training3.setActivityType("Cycling");

        trainingRepository.save(training1);
        trainingRepository.save(training2);
        trainingRepository.save(training3);

        // When
        List<Training> runningTrainings = trainingService.getTrainingsByActivityType("Running");

        // Then
        assertNotNull(runningTrainings);
        assertEquals(0, runningTrainings.size());
        assertTrue(runningTrainings.stream().allMatch(t -> t.getActivityType().equals("Running")));
    }
}
