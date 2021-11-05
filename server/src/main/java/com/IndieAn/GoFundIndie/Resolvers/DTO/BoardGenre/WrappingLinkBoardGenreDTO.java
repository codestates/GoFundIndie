package com.IndieAn.GoFundIndie.Resolvers.DTO.BoardGenre;

import lombok.Builder;

@Builder
public class WrappingLinkBoardGenreDTO {
    private int code;
    private LinkBoardGenreDTO data;

    public WrappingLinkBoardGenreDTO() {}

    public WrappingLinkBoardGenreDTO(int code, LinkBoardGenreDTO data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LinkBoardGenreDTO getData() {
        return data;
    }

    public void setData(LinkBoardGenreDTO data) {
        this.data = data;
    }
}
