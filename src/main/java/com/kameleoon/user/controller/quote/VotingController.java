package com.kameleoon.user.controller.quote;

import com.kameleoon.user.service.quote.VotingService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/voting")
public class VotingController {
    private final VotingService votingService;

    @Autowired
    public VotingController(VotingService votingService) {
        this.votingService = votingService;
    }

    @GetMapping("/voting-for{id}")
    public int votingFor(@PathVariable @Min(0) long id) {
        return votingService.votingFor(id);
    }

    @GetMapping("/voting-against{id}")
    public int votingAgainst(@PathVariable @Min(0) long id) {
        return votingService.votingAgainst(id);
    }
}
