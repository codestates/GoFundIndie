package com.IndieAn.GoFundIndie.Resolvers.DTO.Comment;

import lombok.Builder;

public class CommentGraphQLDTO {
    private long id;
    private int rating;
    private long userId;
    private String userNickname;
    private String profilePicture;
    private Integer donation;
    private String body;
    private boolean spoiler;
    private int like;
    private Boolean ratingChecked;

    public CommentGraphQLDTO() {}

    @Builder
    public CommentGraphQLDTO(long id, int rating, long userId, String userNickname, String profilePicture, Integer donation, String body, boolean spoiler, int like, Boolean ratingChecked) {
        this.id = id;
        this.rating = rating;
        this.userId = userId;
        this.userNickname = userNickname;
        this.profilePicture = profilePicture;
        this.donation = donation;
        this.body = body;
        this.spoiler = spoiler;
        this.like = like;
        this.ratingChecked = ratingChecked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getDonation() {
        return donation;
    }

    public void setDonation(Integer donation) {
        this.donation = donation;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isSpoiler() {
        return spoiler;
    }

    public void setSpoiler(boolean spoiler) {
        this.spoiler = spoiler;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public Boolean getRatingChecked() {
        return ratingChecked;
    }

    public void setRatingChecked(Boolean ratingChecked) {
        this.ratingChecked = ratingChecked;
    }
}
