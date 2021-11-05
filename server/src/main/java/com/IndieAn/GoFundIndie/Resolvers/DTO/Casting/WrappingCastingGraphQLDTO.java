package com.IndieAn.GoFundIndie.Resolvers.DTO.Casting;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WrappingCastingGraphQLDTO {
    private int code;
    private CastingGraphQLDTO data;

    public WrappingCastingGraphQLDTO() {}
}
