package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;

import java.util.List;

public class WrappingLikeBoardGraphQLDTO {
    private int code;
    private List<LikeBoardGraphQLDTO> data;

    public WrappingLikeBoardGraphQLDTO() {}

    @Builder
    public WrappingLikeBoardGraphQLDTO(int code, List<LikeBoardGraphQLDTO> data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<LikeBoardGraphQLDTO> getData() {
        return data;
    }

    public void setData(List<LikeBoardGraphQLDTO> data) {
        this.data = data;
    }
}
