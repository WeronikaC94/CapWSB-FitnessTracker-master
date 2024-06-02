package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingDto;

import java.util.Collection;
import java.util.List;

public interface TrainingService {
    TrainingDto createTraining(TrainingDto trainingDto);
    List<TrainingDto> getAllTrainings();
    List<TrainingDto> getTrainingsByUserId(Long userId);
    List<TrainingDto> getCompletedTrainings(String date);
    List<TrainingDto> getTrainingsByActivityType(ActivityType activity);

    //    @Override
    //    public List<TrainingDto> getTrainingsByActivity(String activityTypeStr) {
    //        ActivityType ActivityType = "";
    //        return trainingRepository.findByActivityType(ActivityType).stream()
    //                .map(this::mapEntityToDto)
    //                .collect(Collectors.toList());
    //    }
    Collection<Training> getTrainingsByActivityType(String activityTypeStr);

    TrainingDto updateTraining(Long trainingId, TrainingDto trainingDto);
}
