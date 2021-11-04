package com.IndieAn.GoFundIndie.Resolvers.DTO.Casting;

import lombok.Builder;

@Builder
public class PutCastingDTO {
    private long castingId;
    private String name;
    private int position;
    private String image;

    public PutCastingDTO() {}

    public PutCastingDTO(long castingId, String name, int position, String image) {
        this.castingId = castingId;
        this.name = name;
        this.position = position;
        this.image = image;
    }

    public long getCastingId() {
        return castingId;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public String getImage() {
        return image;
    }
}
