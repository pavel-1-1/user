package com.kameleoon.user.entity.quote;

import java.io.Serializable;

public class VotingId implements Serializable {
    private long userId;

    private long quoteId;

    public VotingId() {
    }

    public VotingId(long userId, long quoteId) {
        this.userId = userId;
        this.quoteId = quoteId;
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
}
