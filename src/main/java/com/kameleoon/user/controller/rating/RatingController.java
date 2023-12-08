package com.kameleoon.user.controller.rating;

import com.kameleoon.user.dto.rating.RatingCreateDto;
import com.kameleoon.user.dto.rating.RatingDto;
import com.kameleoon.user.service.rating.RatingService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating")
public class RatingController {
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/create")
    public RatingDto createRating(@Validated @RequestBody RatingCreateDto dto) {
        return ratingService.createRating(dto);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam @Min(0) Long userId, @RequestParam @Min(0) Long ratingId) {
        ratingService.deleteRating(userId, ratingId);
    }

    @PutMapping("/update")
    public RatingDto update(@RequestParam @Min(0) Long rating_id,
                            @RequestParam @NotBlank @Size(max = 255) String content) {
        return ratingService.update(rating_id, content);
    }
}
