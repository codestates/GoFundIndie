package com.IndieAn.GoFundIndie.Resolvers.DTO.Casting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WrappingCreateTempCastingDTO {
    private int code;
    private CreateTempCastingDTO data;

    public WrappingCreateTempCastingDTO() {}
}
