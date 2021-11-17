package com.IndieAn.GoFundIndie.Resolvers.DTO.Casting;

import lombok.Builder;
import org.springframework.util.Assert;

@Builder
public class CreateCastingCompleteDTO {
    private long castingId;
    private String name;
    private int position;

    public CreateCastingCompleteDTO() {}

    public CreateCastingCompleteDTO(Long castingId, String name, Integer position) {
        Assert.notNull(castingId, "castingId is not null");
        Assert.notNull(name, "name is not null");
        Assert.notNull(position, "position is not null");

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
