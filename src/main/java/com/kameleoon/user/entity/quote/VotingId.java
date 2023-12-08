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
}
