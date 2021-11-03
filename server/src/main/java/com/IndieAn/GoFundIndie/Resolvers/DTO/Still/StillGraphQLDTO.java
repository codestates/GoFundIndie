package com.IndieAn.GoFundIndie.Resolvers.DTO.Still;

import com.IndieAn.GoFundIndie.Domain.Entity.Still;
import lombok.Builder;

@Builder
public class StillGraphQLDTO {
    private long id;
    private String image;

    public StillGraphQLDTO() {}

    public StillGraphQLDTO(long id, String image) {
        this.id = id;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public static StillGraphQLDTO from(Still entity) {
        return StillGraphQLDTO.builder()
                .id(entity.getId())
                .image(entity.getImage())
                .build();
    }
}
