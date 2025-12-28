package com.gym.gym_backend.repo;

import com.gym.gym_backend.entity.GymSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GymScheduleRepository
        extends JpaRepository<GymSchedule, Integer> {

    // ================= SAVE / CHECK =================
    Optional<GymSchedule> findByUserIdAndVisitDate(
            Integer userId,
            LocalDate visitDate
    );

    // ================= DELETE (FINAL, GUARANTEED) =================
    void deleteByUserIdAndVisitDate(
            Integer userId,
            LocalDate visitDate
    );

    // ================= CROWD COUNT =================
    @Query("""
        SELECT g.visitTime, COUNT(g)
        FROM GymSchedule g
        WHERE g.visitDate = CURRENT_DATE
        GROUP BY g.visitTime
    """)
    List<Object[]> countCrowdToday();
}
