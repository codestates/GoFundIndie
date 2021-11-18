package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;
import org.springframework.util.Assert;

@Builder
public class PutBoardDTO {
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
    private Boolean infoSubtitle;
    private String infoCreatedDate;

    public PutBoardDTO() {}

    public PutBoardDTO(Long boardId, String title, String infoCountry, String infoCreatedYear, int infoTime, String infoStory, String producer, String distributor, String posterImg, String viewLink, int infoLimit, Boolean infoSubtitle, String infoCreatedDate) {
        Assert.notNull(boardId, "boardId is not null");

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

    public long getBoardId() {
        return boardId;
    }

    public String getTitle() {
        return title;
    }

    public String getInfoCountry() {
        return infoCountry;
    }

    public String getInfoCreatedYear() {
        return infoCreatedYear;
    }

    public int getInfoTime() {
        return infoTime;
    }

    public String getInfoStory() {
        return infoStory;
    }

    public String getProducer() {
        return producer;
    }

    public String getDistributor() {
        return distributor;
    }

    public String getPosterImg() {
        return posterImg;
    }

    public String getViewLink() {
        return viewLink;
    }

    public int getInfoLimit() {
        return infoLimit;
    }

    public Boolean getInfoSubtitle() {
        return infoSubtitle;
    }

    public String getInfoCreatedDate() {
        return infoCreatedDate;
    }
}
