package com.kameleoon.user.service.rating;

import com.kameleoon.user.entity.rating.Rating;
import com.kameleoon.user.global_exception.ResourceNotFoundException;
import com.kameleoon.user.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VotingService {
    private final RatingRepository ratingRepository;

    @Autowired
    public VotingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 3000L))
    public int votingFor(long ratingId) {
        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() ->
                new ResourceNotFoundException("The rating was not found!"));
        int countVoting = rating.getRating() + 1;
        rating.setRating(countVoting);
        ratingRepository.save(rating);
        return countVoting;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 3000L))
    public int votingAgainst(long ratingId) {
        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() ->
                new ResourceNotFoundException("The rating was not found!"));
        int countVoting = rating.getRating() - 1;
        rating.setRating(countVoting);
        ratingRepository.save(rating);
        return countVoting;
    }
}
