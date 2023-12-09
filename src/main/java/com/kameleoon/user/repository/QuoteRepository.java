package com.kameleoon.user.repository;

import com.kameleoon.user.entity.quote.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    @Query(value = """
            WITH deleted AS (DELETE FROM quotes WHERE user_id = :userId and id = :quoteId is true RETURNING *)
            SELECT count(*) FROM deleted;
            """, nativeQuery = true)
    int deleteByIdByUserId(@Param("userId") Long userId, @Param("quoteId") Long quoteId);

    @Query(value = """
            SELECT r.id FROM Quote r 
            """)
    List<Long> arrId();

    @Query(value = """
            select * from quotes q order by q.rating desc limit 10
            """, nativeQuery = true)
    List<Quote> top10();

    @Query(value = """
            select * from quotes q order by q.rating limit 10
            """, nativeQuery = true)
    List<Quote> last10();
}
