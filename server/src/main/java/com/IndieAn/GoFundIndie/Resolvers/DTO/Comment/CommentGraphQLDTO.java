package com.IndieAn.GoFundIndie.Resolvers.DTO.Comment;

import lombok.Builder;

@Builder
public class CommentGraphQLDTO {
    private long id;
    private int rating;
    private long userId;
    private String userNickname;
    private String profilePicture;
    private int donation;
    private String body;
    private boolean spoiler;
    private int like;
    private Boolean ratingChecked;

    public CommentGraphQLDTO() {}

    public CommentGraphQLDTO(long id, int rating, long userId, String userNickname, String profilePicture, int donation, String body, boolean spoiler, int like, boolean ratingChecked) {
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

    public int getRating() {
        return rating;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public int getDonation() {
        return donation;
    }

    public String getBody() {
        return body;
    }

    public boolean isSpoiler() {
        return spoiler;
    }

    public int getLike() {
        return like;
    }

    public boolean isRatingChecked() {
        return ratingChecked;
    }
}
