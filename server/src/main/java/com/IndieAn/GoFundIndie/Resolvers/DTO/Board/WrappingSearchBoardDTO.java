package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;

import java.util.List;

public class WrappingSearchBoardDTO {
    private int code;
    private List<SearchBoardDTO> data;

    public WrappingSearchBoardDTO() {}

    @Builder
    public WrappingSearchBoardDTO(int code, List<SearchBoardDTO> data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<SearchBoardDTO> getData() {
        return data;
    }

    public void setData(List<SearchBoardDTO> data) {
        this.data = data;
    }
}
