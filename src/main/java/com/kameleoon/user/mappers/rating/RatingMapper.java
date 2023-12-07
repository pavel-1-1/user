package com.kameleoon.user.mappers.rating;

import com.kameleoon.user.dto.rating.RatingDto;
import com.kameleoon.user.entity.rating.Rating;
import com.kameleoon.user.mappers.Mappers;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RatingMapper extends Mappers<Rating, RatingDto> {
}
