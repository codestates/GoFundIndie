package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
@Builder
public class CreateBoardCompleteDTO {
    private long boardId;
    private String title;
    private String infoCountry;
    private String infoCreatedYear;
    private int infoTime;
    private String infoStory;

    private String producer;
    private String distributor;
    private String posterImg;
    private String viewLink;
    private int infoLimit;
    private boolean infoSubtitle;
    private String infoCreatedDate;

    public CreateBoardCompleteDTO() {}

    public CreateBoardCompleteDTO(Long boardId, String title, String infoCountry,
                                  String infoCreatedYear, Integer infoTime, String infoStory,
                                  String producer, String distributor, String posterImg,
                                  String viewLink, int infoLimit, boolean infoSubtitle,
                                  String infoCreatedDate) {
        Assert.notNull(boardId, "boardId is not null");
        Assert.notNull(title, "title is not null");
        Assert.notNull(infoCountry, "infoCountry is not null");
        Assert.notNull(infoCreatedYear, "infoCreatedYear is not null");
        Assert.notNull(infoTime, "infoTime is not null");
        Assert.notNull(infoStory, "infoStory is not null");

        this.boardId = boardId;
        this.title = title;
        this.infoCountry = infoCountry;
        this.infoCreatedYear = infoCreatedYear;
        this.infoTime = infoTime;
        this.infoStory = infoStory;
        this.producer = producer;
        this.distributor = distributor;
        this.posterImg = posterImg;
        this.viewLink = viewLink;
        this.infoLimit = infoLimit;
        this.infoSubtitle = infoSubtitle;
        this.infoCreatedDate = infoCreatedDate;
    }
}
