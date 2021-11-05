package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WrappingAdminViewBoardDTO {
    private int code;
    private AdminViewBoardDTO data;

    public WrappingAdminViewBoardDTO() {}
}
