package com.kameleoon.user.dto.quote;

import jakarta.validation.constraints.Size;

public class QuoteCreateDto {
    private long userId;
    @Size(min = 10, max = 255, message = "max 255")
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
