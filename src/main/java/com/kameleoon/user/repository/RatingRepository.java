package com.kameleoon.user.repository;

import com.kameleoon.user.entity.rating.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Modifying
    @Query(value = """
            delete from quotes as r where r.user_id = :userId and r.id = :ratingId
            """, nativeQuery = true)
    int deleteByIdByUserId(Long userId, Long ratingId);

    @Query(value = """
            SELECT r.id FROM Rating r 
            """)
    List<Long> arrId();
}
