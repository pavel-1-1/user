package com.kameleoon.user.repository;

import com.kameleoon.user.entity.quote.VotingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingScheduleRepository extends JpaRepository<VotingSchedule, Long> {
}
