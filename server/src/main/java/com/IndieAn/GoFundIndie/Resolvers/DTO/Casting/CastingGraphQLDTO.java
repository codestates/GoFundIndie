package com.IndieAn.GoFundIndie.Resolvers.DTO.Casting;

import com.IndieAn.GoFundIndie.Domain.Entity.Casting;
import lombok.Builder;

@Builder
public class CastingGraphQLDTO {
    private long id;
    private String name;
    private int position;
    private String image;

    public long getId() {
        return id;
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

    public CastingGraphQLDTO() {}

    public CastingGraphQLDTO(long id, String name, int position, String image) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.image = image;
    }

    public static CastingGraphQLDTO from(Casting en) {
        return CastingGraphQLDTO.builder()
                .id(en.getId())
                .name(en.getName())
                .position(en.getPosition())
                .image(en.getImage())
                .build();
    }
}
