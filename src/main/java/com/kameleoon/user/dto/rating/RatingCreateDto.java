package com.kameleoon.user.dto.rating;

public class RatingCreateDto {
    private long user_id;
    private String content;

    public RatingCreateDto() {
    }

    public RatingCreateDto(long user_id, String content) {
        this.user_id = user_id;
        this.content = content;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
