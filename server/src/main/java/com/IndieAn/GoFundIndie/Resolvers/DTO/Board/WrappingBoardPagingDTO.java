package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;

public class WrappingBoardPagingDTO {
    private int code;
    private BoardPagingDTO data;

    public WrappingBoardPagingDTO() {}

    @Builder
    public WrappingBoardPagingDTO(int code, BoardPagingDTO data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BoardPagingDTO getData() {
        return data;
    }

    public void setData(BoardPagingDTO data) {
        this.data = data;
    }
}
