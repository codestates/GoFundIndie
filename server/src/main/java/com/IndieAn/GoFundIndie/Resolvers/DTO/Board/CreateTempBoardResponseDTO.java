package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateTempBoardResponseDTO {
    private int code;
    private TempBoardDTO data;

    public CreateTempBoardResponseDTO() {}
}
