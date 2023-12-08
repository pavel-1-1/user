package com.kameleoon.user.service.rating;


import com.kameleoon.user.dto.rating.RatingCreateDto;
import com.kameleoon.user.dto.rating.RatingDto;
import com.kameleoon.user.entity.rating.Rating;
import com.kameleoon.user.entity.user.User;
import com.kameleoon.user.global_exception.RequestError;
import com.kameleoon.user.global_exception.ResourceNotFoundException;
import com.kameleoon.user.mappers.rating.RatingMapper;
import com.kameleoon.user.repository.RatingRepository;
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
public class RatingService {
    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;

    @Autowired
    public RatingService(RatingRepository ratingRepository, RatingMapper ratingMapper,
                         UserRepository userRepository) {
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }

    @Transactional
    public RatingDto createRating(RatingCreateDto dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException("Such is the user's no!"));
        Rating rating = new Rating();
        rating.setContent(dto.getContent());
        rating.setUser(user);
        return ratingMapper.toDto(ratingRepository.save(rating));
    }

    public void deleteRating(Long userId, Long ratingId) {
        if (ratingRepository.deleteByIdByUserId(userId, ratingId) == 0)
            throw new RequestError("The quote has not been deleted!");
    }

    @Transactional
    public RatingDto update(long ratingId, String content) {
        Rating rating = ratingRepository.findById(ratingId).orElseThrow(() ->
                new ResourceNotFoundException("The quote was not found!"));
        rating.setContent(content);
        return ratingMapper.toDto(ratingRepository.save(rating));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 5, backoff = @Backoff(value = 500))
    public RatingDto getRating(long ratingId) {
        return ratingMapper.toDto(ratingRepository.findById(ratingId).orElseThrow(() ->
                new ResourceNotFoundException("The quote was not found!")));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 5, backoff = @Backoff(value = 500))
    public RatingDto getRandom() {
        List<Long> list = ratingRepository.arrId();
        long id = list.get(new Random().nextInt(list.size()));
        return ratingMapper.toDto(ratingRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("The quote was not found!")));
    }
}
