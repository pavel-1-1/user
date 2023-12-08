package com.kameleoon.user.service.quote;

import com.kameleoon.user.entity.quote.Quote;
import com.kameleoon.user.global_exception.ResourceNotFoundException;
import com.kameleoon.user.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VotingService {
    private final QuoteRepository ratingRepository;

    @Autowired
    public VotingService(QuoteRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 500L))
    public int votingFor(long quoteId) {
        Quote quote = findById(quoteId);
        int countVoting = quote.getRating() + 1;
        quote.setRating(countVoting);
        ratingRepository.save(quote);
        return countVoting;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 500L))
    public int votingAgainst(long quoteId) {
        Quote quote = findById(quoteId);
        int countVoting = quote.getRating() - 1;
        quote.setRating(countVoting);
        ratingRepository.save(quote);
        return countVoting;
    }

    private Quote findById(long id) {
        return ratingRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("The quote was not found!"));
    }
}
