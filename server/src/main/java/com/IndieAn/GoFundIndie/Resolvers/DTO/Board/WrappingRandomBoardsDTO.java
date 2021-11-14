package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;
import org.springframework.util.Assert;

import java.util.List;

public class WrappingRandomBoardsDTO {
    private Integer code;
    private List<RandomBoardDTO> data;

    public WrappingRandomBoardsDTO() {
    }

    @Builder
    public WrappingRandomBoardsDTO(Integer code, List<RandomBoardDTO> data) {
        Assert.notNull(code, "code is not null");

        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RandomBoardDTO> getData() {
        return data;
    }

    public void setData(List<RandomBoardDTO> data) {
        this.data = data;
    }
}
