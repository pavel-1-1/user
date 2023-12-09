package com.kameleoon.user.dto.quote;

import java.util.List;

public class VotingScheduleDto {
    private List<String> time;
    private List<Integer> rating;

    public VotingScheduleDto(List<String> time, List<Integer> rating) {
        this.time = time;
        this.rating = rating;
    }

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Integer> getRating() {
        return rating;
    }

    public void setRating(List<Integer> rating) {
        this.rating = rating;
    }
}
