package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WrappingViewBoardDTO {
    private int code;
    private ViewBoardDTO data;

    public WrappingViewBoardDTO() {}
}
