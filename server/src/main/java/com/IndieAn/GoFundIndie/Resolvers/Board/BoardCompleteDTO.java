package com.IndieAn.GoFundIndie.Resolvers.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@Builder
public class BoardCompleteDTO {

    @NotBlank(message = "4006 : user cannot be blank")
    private long userId;

    @NotBlank(message = "4006 : title cannot be blank")
    private String title;

    @NotBlank(message = "4006 : infoCountry cannot be blank")
    private String infoCountry;

    @NotBlank(message = "4006 : infoCreatedAt cannot be blank")
    private String infoCreatedAt;

    @NotBlank(message = "4006 : infoTime cannot be blank")
    private int infoTime;

    @NotBlank(message = "4006 : infoStory cannot be blank")
    private String infoStory;

    private String producer;
    private String distributor;
    private String posterImg;
    private String viewLink;
    private int infoLimit;
    private boolean infoSubtitle;
    private Date createdAt;

    public BoardCompleteDTO() {}

    public BoardCompleteDTO(long userId, String title, String infoCountry,
                            String infoCreatedAt, int infoTime, String infoStory,
                            String producer, String distributor, String posterImg,
                            String viewLink, int infoLimit, boolean infoSubtitle,
                            Date createdAt) {
        this.userId = userId;
        this.title = title;
        this.infoCountry = infoCountry;
        this.infoCreatedAt = infoCreatedAt;
        this.infoTime = infoTime;
        this.infoStory = infoStory;
        this.producer = producer;
        this.distributor = distributor;
        this.posterImg = posterImg;
        this.viewLink = viewLink;
        this.infoLimit = infoLimit;
        this.infoSubtitle = infoSubtitle;
        this.createdAt = createdAt;
    }
}
