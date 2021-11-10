package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public class BoardGraphQLDTO {
    private long id;
    private boolean isApprove;
    private String title;
    private String posterImg;
    private String infoCountry;
    private String infoCreatedYear;
    private String infoCreatedDate;
    private int infoTime;
    private int infoLimit;

    public BoardGraphQLDTO() {}

    public BoardGraphQLDTO(long id, boolean isApprove, String title, String posterImg, String infoCountry, String infoCreatedYear, String infoCreatedDate, int infoTime, int infoLimit) {
        this.id = id;
        this.isApprove = isApprove;
        this.title = title;
        this.posterImg = posterImg;
        this.infoCountry = infoCountry;
        this.infoCreatedYear = infoCreatedYear;
        this.infoCreatedDate = infoCreatedDate;
        this.infoTime = infoTime;
        this.infoLimit = infoLimit;
    }

    public static BoardGraphQLDTO from(Board en) {
        return BoardGraphQLDTO.builder()
                .id(en.getId())
                .isApprove(en.isApprove())
                .title(en.getTitle())
                .posterImg(en.getPosterImg())
                .infoCountry(en.getInfoCountry())
                .infoCreatedYear(en.getInfoCreatedYear())
                .infoCreatedDate(en.getInfoCreatedDate())
                .infoTime(en.getInfoTime())
                .infoLimit(en.getInfoLimit())
                .build();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isApprove() {
        return isApprove;
    }

    public void setApprove(boolean approve) {
        isApprove = approve;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterImg() {
        return posterImg;
    }

    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg;
    }

    public String getInfoCountry() {
        return infoCountry;
    }

    public void setInfoCountry(String infoCountry) {
        this.infoCountry = infoCountry;
    }

    public String getInfoCreatedYear() {
        return infoCreatedYear;
    }

    public void setInfoCreatedYear(String infoCreatedYear) {
        this.infoCreatedYear = infoCreatedYear;
    }

    public String getInfoCreatedDate() {
        return infoCreatedDate;
    }

    public void setInfoCreatedDate(String infoCreatedDate) {
        this.infoCreatedDate = infoCreatedDate;
    }

    public int getInfoTime() {
        return infoTime;
    }

    public void setInfoTime(int infoTime) {
        this.infoTime = infoTime;
    }

    public int getInfoLimit() {
        return infoLimit;
    }

    public void setInfoLimit(int infoLimit) {
        this.infoLimit = infoLimit;
    }
}
