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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Transactional
    public void deleteRating(Long userId, Long ratingId) {
        if (ratingRepository.deleteByIdByUserId(userId, ratingId) == 0)
            throw new RequestError("The quote has not been deleted!");
    }

    @Transactional
    public RatingDto update(long rating_id, String content) {
        Rating rating = ratingRepository.findById(rating_id).orElseThrow(() ->
                new ResourceNotFoundException("The quote was not found!"));
        rating.setContent(content);
        return ratingMapper.toDto(ratingRepository.save(rating));
    }
}
