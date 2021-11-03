package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ViewWrappingBoardsDTO {
    private int code;
    private List<BoardGraphQLDTO> data;

    public ViewWrappingBoardsDTO() {}
}
