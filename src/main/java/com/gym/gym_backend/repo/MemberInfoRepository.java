package com.gym.gym_backend.repo;

import com.gym.gym_backend.entity.MemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberInfoRepository
        extends JpaRepository<MemberInfo, Integer> {

    Optional<MemberInfo> findByUserId(Integer userId);
}
