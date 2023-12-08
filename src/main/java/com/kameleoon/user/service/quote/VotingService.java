package com.kameleoon.user.service.quote;

import com.kameleoon.user.entity.quote.Quote;
import com.kameleoon.user.entity.quote.Voting;
import com.kameleoon.user.global_exception.RequestError;
import com.kameleoon.user.global_exception.ResourceNotFoundException;
import com.kameleoon.user.repository.QuoteRepository;
import com.kameleoon.user.repository.VotingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class VotingService {
    private final QuoteRepository ratingRepository;

    private final VotingRepository votingRepository;

    @Autowired
    public VotingService(QuoteRepository ratingRepository,
                         VotingRepository votingRepository) {
        this.ratingRepository = ratingRepository;
        this.votingRepository = votingRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 500L), retryFor = SQLException.class)
    public int votingFor(Long userId, Long quoteId) {
        Optional<Voting> votingOp = findByUserIdAndQuoteId(userId, quoteId);
        Quote quote = findById(quoteId);
        Voting voting;
        if (votingOp.isPresent()) {
            voting = votingOp.get();
            if (voting.getVoting() <= 0) {
                voting.setVoting(1);
            } else {
                throw new RequestError("You have already voted");
            }
        } else {
            voting = new Voting();
            voting.setVoting(1);
            voting.setUserId(userId);
            voting.setQuoteId(quoteId);
        }
        int countVoting = quote.getRating() + 1;
        quote.setRating(countVoting);
        ratingRepository.save(quote);
        votingRepository.save(voting);
        return countVoting;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 500L), retryFor = SQLException.class)
    public int votingAgainst(Long userId, Long quoteId) {
        Optional<Voting> votingOp = findByUserIdAndQuoteId(userId, quoteId);
        Quote quote = findById(quoteId);
        Voting voting;
        if (votingOp.isPresent()) {
            voting = votingOp.get();
            if (voting.getVoting() >= 0) {
                voting.setVoting(-1);
            } else {
                throw new RequestError("You have already voted");
            }
        } else {
            voting = new Voting();
            voting.setVoting(-1);
            voting.setUserId(userId);
            voting.setQuoteId(quoteId);
        }
        int countVoting = quote.getRating() - 1;
        quote.setRating(countVoting);
        ratingRepository.save(quote);
        votingRepository.save(voting);
        return countVoting;
    }

    private Quote findById(Long id) {
        return ratingRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("The quote was not found!"));
    }

    private Optional<Voting> findByUserIdAndQuoteId(Long userId, Long quoteId) {
        return votingRepository.findByUserIdAndQuoteId(userId, quoteId);
    }
}
