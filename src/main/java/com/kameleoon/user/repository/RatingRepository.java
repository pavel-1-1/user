package com.kameleoon.user.repository;

import com.kameleoon.user.entity.rating.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Modifying
    @Query(value = """
            delete from quotes as r where r.user_id = :user_id and r.id = :rating_id
            """, nativeQuery = true)
    int deleteByIdByUserId(Long user_id, Long rating_id);
}
