package com.gym.gym_backend.repo;

import com.gym.gym_backend.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(value = """
    SELECT COUNT(*)
    FROM subscription
    WHERE end_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL 7 DAY)
""", nativeQuery = true)
    long countExpiringSoon();



    @Query(value = """
    SELECT COUNT(*)
    FROM subscription
    WHERE end_date >= CURDATE()
""", nativeQuery = true)
    long countActive();

}






