package com.kameleoon.user.entity.quote;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "voting")
public class Voting {
    @Id
    @Column(name = "user_id")
    private long userId;

    @Id
    @Column(name = "quote_id")
    private long quoteId;

    @Column(name = "voting_for_against", length = 3)
    private int voting;

    public Voting() {
    }

    public Voting(long userId, long quoteId, int voting) {
        this.userId = userId;
        this.quoteId = quoteId;
        this.voting = voting;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(long quoteId) {
        this.quoteId = quoteId;
    }

    public int getVoting() {
        return voting;
    }

    public void setVoting(int voting) {
        this.voting = voting;
    }
}
