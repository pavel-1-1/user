package com.kameleoon.user.service.quote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kameleoon.user.dto.quote.VotingScheduleDto;
import com.kameleoon.user.entity.quote.Quote;
import com.kameleoon.user.entity.quote.Voting;
import com.kameleoon.user.entity.quote.VotingSchedule;
import com.kameleoon.user.global_exception.RequestError;
import com.kameleoon.user.global_exception.ResourceNotFoundException;
import com.kameleoon.user.repository.QuoteRepository;
import com.kameleoon.user.repository.VotingRepository;
import com.kameleoon.user.repository.VotingScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class VotingService {
    private final QuoteRepository ratingRepository;

    private final VotingRepository votingRepository;

    private final VotingScheduleRepository votingScheduleRepository;

    private final ObjectMapper mapper;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    @Autowired
    public VotingService(QuoteRepository ratingRepository,
                         VotingRepository votingRepository, ObjectMapper mapper,
                         VotingScheduleRepository votingScheduleRepository) {
        this.ratingRepository = ratingRepository;
        this.votingRepository = votingRepository;
        this.mapper = mapper;
        this.votingScheduleRepository = votingScheduleRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 500L), retryFor = SQLException.class)
    public int votingFor(Long userId, Long quoteId) {
        Optional<Voting> votingOp = findByUserIdAndQuoteId(userId, quoteId);
        Quote quote = findById(quoteId);
        Voting voting;
        if (votingOp.isPresent()) {
            voting = votingOp.get();
            int checkVoting = voting.getVoting();
            if (checkVoting < 0) {
                voting.setVoting(0);
            } else if (checkVoting == 0) {
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
        votingScheduleRepository.save(updateSchedule(quote));
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
            int checkVoting = voting.getVoting();
            if (checkVoting > 0) {
                voting.setVoting(0);
            } else if (checkVoting == 0) {
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
        int countVoting = quote.getRating() + (-1);
        quote.setRating(countVoting);
        votingScheduleRepository.save(updateSchedule(quote));
        ratingRepository.save(quote);
        votingRepository.save(voting);
        return countVoting;
    }

    @Transactional(isolation = Isolation.DEFAULT)
    public VotingScheduleDto votingSchedule(Long quoteId) {
        VotingSchedule votingSchedule = votingScheduleRepository.findById(quoteId).orElseThrow(() ->
                new ResourceNotFoundException("There is no schedule"));
        List<String> times;
        List<Integer> ratings;
        try {
            times = mapper.readValue(votingSchedule.getUpdateAt(), new TypeReference<>() {
            });
            ratings = mapper.readValue(votingSchedule.getRating(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RequestError("Please try again!");
        }
        System.out.println(times + "-------------------" + ratings);
        return new VotingScheduleDto(times, ratings);
    }

    @Transactional
    private VotingSchedule updateSchedule(Quote quote) {
        VotingSchedule votingSchedule = votingScheduleRepository.findById(quote.getId()).orElseThrow(() ->
                new ResourceNotFoundException("There is no schedule"));
        String jsonArrRating = votingSchedule.getRating().replace("]", "," + quote.getRating() + "]");
        String jsonArrTime = votingSchedule.getUpdateAt().replace("]", "," + formatter.format(quote.getUpdatedAt())
                + "]");
        votingSchedule.setRating(jsonArrRating);
        votingSchedule.setUpdateAt(jsonArrTime);
        return votingSchedule;
    }

    @Transactional
    private Quote findById(Long id) {
        return ratingRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("The quote was not found!"));
    }

    @Transactional
    private Optional<Voting> findByUserIdAndQuoteId(Long userId, Long quoteId) {
        return votingRepository.findByUserIdAndQuoteId(userId, quoteId);
    }
}
