package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.exception.api.NotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.Training;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;
    private final TrainingMapper trainingMapper;

    @Autowired
    public TrainingServiceImpl(TrainingRepository trainingRepository, UserRepository userRepository, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.userRepository = userRepository;
        this.trainingMapper = trainingMapper;
    }

    @Override
    public TrainingDto createTraining(TrainingDto trainingDto) {
        Training training = mapDtoToEntity(trainingDto);
        Training savedTraining = trainingRepository.save(training);
        return mapEntityToDto(savedTraining);
    }

    @Override
    public List<TrainingDto> getAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> getTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> getCompletedTrainings(String date) {
        LocalDate parsedDate = parseDate(date);
        return trainingRepository.findByDateBefore(parsedDate).stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> getTrainingsByActivityType(ActivityType activity) {
        return List.of();
    }

    @Override
    public List<Training> getTrainingsByActivityType(String activityTypeStr) {
        ActivityType activityType = ActivityType.valueOf(activityTypeStr.toUpperCase());
        return trainingRepository.findByActivityType(activityType);
    }

    @Override
    public TrainingDto updateTraining(Long trainingId, TrainingDto trainingDto) {
        Training existingTraining = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new NotFoundException("Training not found with id: " + trainingId));

        if (trainingDto.getActivityType() != null) {
            existingTraining.setActivity(ActivityType.valueOf(trainingDto.getActivityType()));
        }
        if (trainingDto.getDistance() > 0) {
            existingTraining.setDistance(trainingDto.getDistance());
        }

        Training updatedTraining = trainingRepository.save(existingTraining);
        return mapEntityToDto(updatedTraining);
    }

    private Training mapDtoToEntity(TrainingDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + dto.getUserId()));
        return new Training(
                user,
                parseDate(dto.getStartTime()),
                parseDate(dto.getEndTime()),
                ActivityType.valueOf(dto.getActivityType()),
                dto.getDistance(),
                dto.getAverageSpeed()
        );
    }

    private TrainingDto mapEntityToDto(Training training) {
        return new TrainingDto(
                training.getId(),
                training.getUser().getId(),
                training.getStartTime().toString(),
                training.getEndTime().toString(),
                training.getActivityType().name(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    private LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString);
    }

    private Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }



    public Training addTraining(Training training) {
        return trainingRepository.save(training);
    }


    public void deleteTraining(Long id) {
        trainingRepository.deleteById(id);
    }


    public Training updateTraining(Training savedTraining) {
        return trainingRepository.save(savedTraining);
    }
}
