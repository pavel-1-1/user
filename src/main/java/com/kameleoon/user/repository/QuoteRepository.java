package com.kameleoon.user.repository;

import com.kameleoon.user.entity.quote.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    @Modifying
    @Query(value = """
            delete from quotes as r where r.user_id = :userId and r.id = :quoteId
            """, nativeQuery = true)
    int deleteByIdByUserId(Long userId, Long quoteId);

    @Query(value = """
            SELECT r.id FROM Quote r 
            """)
    List<Long> arrId();
}
