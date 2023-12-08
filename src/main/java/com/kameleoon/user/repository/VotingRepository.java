package com.kameleoon.user.repository;

import com.kameleoon.user.entity.quote.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotingRepository extends JpaRepository<Voting, Long> {
}
