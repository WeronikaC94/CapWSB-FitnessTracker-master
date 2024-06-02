package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByActivityType(ActivityType activityType);

    Collection<Training> findByDateBefore(LocalDate parsedDate);

    Collection<Training> findByUserId(Long userId);
}
