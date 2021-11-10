package com.IndieAn.GoFundIndie.Domain.DTO;

import lombok.Data;
//
@Data
public class CommentOutputDTO {
    private Long id;
    private int rating;
    private long userId;
    private String userNickname;
    private String profilePicture;
    private int donation;
    private String body;
    private boolean spoiler;
    private int like;
    private boolean ratingChecked;

    public CommentOutputDTO(Long id, int rating, long userId, String userNickname, String profilePicture,int donation, String body, boolean spoiler, int like, boolean ratingChecked) {
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
}
