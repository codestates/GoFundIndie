package com.IndieAn.GoFundIndie.Resolvers.DTO.Casting;

import lombok.Builder;

@Builder
public class CreateTempCastingDTO {
    private long id;

    public CreateTempCastingDTO() {}

    public CreateTempCastingDTO(long id) { this.id = id; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
