package com.kameleoon.user.dto.rating;

public class RatingCreateDto {
    private long userId;
    private String content;

    public RatingCreateDto() {
    }

    public RatingCreateDto(long userId, String content) {
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
