package com.kameleoon.user.dto.quote;

public class QuoteCreateDto {
    private long userId;
    private String content;

    public QuoteCreateDto() {
    }

    public QuoteCreateDto(long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
