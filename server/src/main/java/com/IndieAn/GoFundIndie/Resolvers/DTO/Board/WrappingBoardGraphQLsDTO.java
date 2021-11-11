package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;
import org.springframework.util.Assert;

import java.util.List;

public class WrappingBoardGraphQLsDTO {
    private int code;
    private List<BoardGraphQLDTO> data;

    public WrappingBoardGraphQLsDTO() {}

    @Builder
    public WrappingBoardGraphQLsDTO(int code, List<BoardGraphQLDTO> data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public List<BoardGraphQLDTO> getData() {
        return data;
    }
}
