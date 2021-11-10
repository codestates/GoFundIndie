package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WrappingCreateTempBoardDTO {
    private int code;
    private CreateTempBoardDTO data;

    public WrappingCreateTempBoardDTO() {}
}
