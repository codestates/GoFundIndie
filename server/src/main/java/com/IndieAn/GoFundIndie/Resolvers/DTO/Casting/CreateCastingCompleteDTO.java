package com.IndieAn.GoFundIndie.Resolvers.DTO.Casting;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public class CreateCastingCompleteDTO {
    @NotBlank
    private long castingId;

    @NotBlank
    private String name;

    @NotBlank
    private int position;

    public CreateCastingCompleteDTO() {}

    public CreateCastingCompleteDTO(long castingId, String name, int position) {
        this.castingId = castingId;
        this.name = name;
        this.position = position;
    }

    public long getCastingId() {
        return castingId;
    }

    public void setCastingId(long castingId) {
        this.castingId = castingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
