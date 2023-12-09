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

import java.util.List;

@RestController
@RequestMapping("/api/v1/quote")
public class QuoteController {
    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @PostMapping("/create")
    public QuoteDto createRating(@Validated @RequestBody QuoteCreateDto dto) {
        return quoteService.createQuote(dto);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam @Min(0) Long userId, @RequestParam @Min(0) Long quoteId) {
        quoteService.deleteQuote(userId, quoteId);
    }

    @PutMapping("/update")
    public QuoteDto update(@RequestParam @Min(0) Long quoteId,
                           @RequestParam @NotBlank @Size(max = 255) String content) {
        return quoteService.update(quoteId, content);
    }

    @GetMapping("/get{quoteId}")
    public QuoteDto get(@PathVariable @Min(0) Long quoteId) {
        return quoteService.getQuote(quoteId);
    }

    @GetMapping("/get-random")
    public QuoteDto getRandom() {
        return quoteService.getRandom();
    }

    @GetMapping("/get-top-10")
    public List<QuoteDto> top10Quote() {
        return quoteService.top10Quote();
    }

    @GetMapping("/get-last-10")
    public List<QuoteDto> last10Quote() {
        return quoteService.last10Quote();
    }
}
