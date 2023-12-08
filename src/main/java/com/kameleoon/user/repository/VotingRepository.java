package com.kameleoon.user.repository;

import com.kameleoon.user.entity.quote.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VotingRepository extends JpaRepository<Voting, Long> {
    @Query(value = """
            SELECT v FROM Voting v WHERE v.userId = :userId and v.quoteId = :quoteId
              """)
    Optional<Voting> findByUserIdAndQuoteId(Long userId, Long quoteId);

}
