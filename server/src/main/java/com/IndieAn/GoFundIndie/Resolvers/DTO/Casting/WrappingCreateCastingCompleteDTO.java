package com.IndieAn.GoFundIndie.Resolvers.DTO.Casting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WrappingCreateCastingCompleteDTO {
    private int code;
    private CreateCastingCompleteDTO data;

    public WrappingCreateCastingCompleteDTO() {}
}
