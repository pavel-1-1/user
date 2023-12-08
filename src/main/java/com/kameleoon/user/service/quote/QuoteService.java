package com.kameleoon.user.service.quote;


import com.kameleoon.user.dto.quote.QuoteCreateDto;
import com.kameleoon.user.dto.quote.QuoteDto;
import com.kameleoon.user.entity.quote.Quote;
import com.kameleoon.user.entity.user.User;
import com.kameleoon.user.global_exception.RequestError;
import com.kameleoon.user.global_exception.ResourceNotFoundException;
import com.kameleoon.user.mappers.quote.QuoteMapper;
import com.kameleoon.user.repository.QuoteRepository;
import com.kameleoon.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
public class QuoteService {
    private final UserRepository userRepository;
    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;

    @Autowired
    public QuoteService(QuoteRepository quoteRepository, QuoteMapper quoteMapper,
                        UserRepository userRepository) {
        this.userRepository = userRepository;
        this.quoteRepository = quoteRepository;
        this.quoteMapper = quoteMapper;
    }

    @Transactional
    public QuoteDto createQuote(QuoteCreateDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException("Such is the user's no!"));
        Quote quote = new Quote();
        quote.setContent(dto.getContent());
        quote.setUser(user);
        return quoteMapper.toDto(quoteRepository.save(quote));
    }

    public void deleteRating(Long userId, Long ratingId) {
        if (quoteRepository.deleteByIdByUserId(userId, ratingId) == 0)
            throw new RequestError("The quote has not been deleted!");
    }

    @Transactional
    public QuoteDto update(long quoteId, String content) {
        Quote quote = quoteRepository.findById(quoteId).orElseThrow(() ->
                new ResourceNotFoundException("The quote was not found!"));
        quote.setContent(content);
        return quoteMapper.toDto(quoteRepository.save(quote));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 5, backoff = @Backoff(value = 500))
    public QuoteDto getQuote(long quoteId) {
        return quoteMapper.toDto(quoteRepository.findById(quoteId).orElseThrow(() ->
                new ResourceNotFoundException("The quote was not found!")));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 5, backoff = @Backoff(value = 500))
    public QuoteDto getRandom() {
        List<Long> list = quoteRepository.arrId();
        long id = list.get(new Random().nextInt(list.size()));
        return quoteMapper.toDto(quoteRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("The quote was not found!")));
    }
}
