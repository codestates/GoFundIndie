package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import lombok.Builder;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class LikeBoardGraphQLDTO {
    private long id;
    private boolean isApprove;
    private String title;
    private String posterImg;
    private String infoCountry;
    private String infoCreatedYear;
    private String infoCreatedDate;
    private Integer infoTime;
    private Integer infoLimit;
    private String infoStory;
    private List<GenreGraphQLDTO> genre;

    public LikeBoardGraphQLDTO() {}

    @Builder
    public LikeBoardGraphQLDTO(Board board, boolean isApprove, String title, String posterImg,
                               String infoCountry, String infoCreatedYear,
                               String infoCreatedDate, Integer infoTime,
                               Integer infoLimit, String infoStory) {
        Assert.notNull(title, "title is not null");
        Assert.notNull(infoStory, "infoStory is not null");

        this.id = board.getId();
        this.isApprove = isApprove;
        this.title = title;
        this.posterImg = posterImg;
        this.infoCountry = infoCountry;
        this.infoCreatedYear = infoCreatedYear;
        this.infoCreatedDate = infoCreatedDate;
        this.infoTime = infoTime;
        this.infoLimit = infoLimit;
        this.infoStory = infoStory;

        // TODO 쿼리 줄이기
        List<GenreGraphQLDTO> list = new ArrayList<>();
        board.getBoardGenres().forEach(el -> list.add(GenreGraphQLDTO.from(el.getGenreId())));
        this.genre = list;
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

    public Integer getInfoTime() {
        return infoTime;
    }

    public void setInfoTime(Integer infoTime) {
        this.infoTime = infoTime;
    }

    public Integer getInfoLimit() {
        return infoLimit;
    }

    public void setInfoLimit(Integer infoLimit) {
        this.infoLimit = infoLimit;
    }

    public String getInfoStory() {
        return infoStory;
    }

    public void setInfoStory(String infoStory) {
        this.infoStory = infoStory;
    }

    public List<GenreGraphQLDTO> getGenre() {
        return genre;
    }

    public void setGenre(List<GenreGraphQLDTO> genre) {
        this.genre = genre;
    }
}
