package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/v1/training")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody TrainingDto trainingDto) {
        TrainingDto createdTraining = trainingService.createTraining(trainingDto);
        return ResponseEntity.ok(createdTraining);
    }

    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings() {
        List<TrainingDto> trainings = trainingService.getAllTrainings();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrainingDto>> getTrainingsByUserId(@PathVariable Long userId) {
        List<TrainingDto> trainings = trainingService.getTrainingsByUserId(userId);
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TrainingDto>> getCompletedTrainings(@RequestParam String date) {
        List<TrainingDto> trainings = trainingService.getCompletedTrainings(date);
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/activity/{activity}")
    public ResponseEntity<Collection<Training>> getTrainingsByActivity(@PathVariable String activity) {
        Collection<Training> trainings = trainingService.getTrainingsByActivityType(activity);
        return ResponseEntity.ok(trainings);
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<TrainingDto> updateTraining(@PathVariable Long trainingId, @RequestBody TrainingDto trainingDto) {
        TrainingDto updatedTraining = trainingService.updateTraining(trainingId, trainingDto);
        return ResponseEntity.ok(updatedTraining);
    }
}
