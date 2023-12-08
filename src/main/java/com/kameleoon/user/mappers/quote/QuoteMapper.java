package com.kameleoon.user.mappers.quote;

import com.kameleoon.user.dto.quote.QuoteDto;
import com.kameleoon.user.entity.quote.Quote;
import com.kameleoon.user.mappers.Mappers;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuoteMapper extends Mappers<Quote, QuoteDto> {
}
