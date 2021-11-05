package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WrappingCreateBoardCompleteDTO {
    private int code;
    private CreateBoardCompleteDTO data;

    public WrappingCreateBoardCompleteDTO() {}
}
