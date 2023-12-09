package com.kameleoon.user.entity.quote;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "voting_schedule")
public class VotingSchedule {
    @Id
    private Long quoteId;

    @Column(name = "update_at")
    private String updateAt;

    @Column(name = "rating")
    private String rating;

    public VotingSchedule() {
    }

    public VotingSchedule(Long quoteId, String updateAt, String rating) {
        this.quoteId = quoteId;
        this.updateAt = updateAt;
        this.rating = rating;
    }

    public Long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Long quoteId) {
        this.quoteId = quoteId;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
