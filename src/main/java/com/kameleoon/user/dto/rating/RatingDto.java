package com.kameleoon.user.dto.rating;

import com.kameleoon.user.dto.user.UserDto;

public class RatingDto {

    private long id;

    private String content;

    private UserDto user;

    private int rating;

    public RatingDto() {
    }

    public RatingDto(long id, String content, UserDto user, int rating) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
