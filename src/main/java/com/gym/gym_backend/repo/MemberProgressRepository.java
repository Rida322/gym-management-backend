package com.gym.gym_backend.repo;

import com.gym.gym_backend.entity.MemberProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberProgressRepository extends JpaRepository<MemberProgress, Integer> {
    List<MemberProgress> findByUserIdOrderByProgressDateAsc(Integer userId);

    @Query("""
        SELECT mp.weight
        FROM MemberProgress mp
        WHERE mp.userId = :userId
        ORDER BY mp.progressDate ASC
    """)
    List<Double> getWeightHistory(@Param("userId") int userId);
}

