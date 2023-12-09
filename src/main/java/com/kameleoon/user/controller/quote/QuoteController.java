package com.kameleoon.user.controller.quote;

import com.kameleoon.user.dto.quote.QuoteCreateDto;
import com.kameleoon.user.dto.quote.QuoteDto;
import com.kameleoon.user.service.quote.QuoteService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quote")
public class QuoteController {
    private final QuoteService QuoteService;

    @Autowired
    public QuoteController(QuoteService QuoteService) {
        this.QuoteService = QuoteService;
    }

    @PostMapping("/create")
    public QuoteDto createRating(@Validated @RequestBody QuoteCreateDto dto) {
        return QuoteService.createQuote(dto);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam @Min(0) Long userId, @RequestParam @Min(0) Long quoteId) {
        QuoteService.deleteQuote(userId, quoteId);
    }

    @PutMapping("/update")
    public QuoteDto update(@RequestParam @Min(0) Long quoteId,
                           @RequestParam @NotBlank @Size(max = 255) String content) {
        return QuoteService.update(quoteId, content);
    }

    @GetMapping("/get{quoteId}")
    public QuoteDto get(@PathVariable @Min(0) Long quoteId) {
        return QuoteService.getQuote(quoteId);
    }

    @GetMapping("/get-random")
    public QuoteDto getRandom() {
        return QuoteService.getRandom();
    }
}
