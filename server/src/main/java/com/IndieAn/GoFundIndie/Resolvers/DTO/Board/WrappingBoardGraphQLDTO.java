package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WrappingBoardGraphQLDTO {
    private int code;
    private BoardGraphQLDTO data;

    public WrappingBoardGraphQLDTO() {}
}
