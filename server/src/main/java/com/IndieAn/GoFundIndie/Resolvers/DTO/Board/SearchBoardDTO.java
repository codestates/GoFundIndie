package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;

public class SearchBoardDTO {
    private long id;
    private String title;
    private String posterImg;

    public SearchBoardDTO() {}

    @Builder
    public SearchBoardDTO(long id, String title, String posterImg) {
        this.id = id;
        this.title = title;
        this.posterImg = posterImg;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
