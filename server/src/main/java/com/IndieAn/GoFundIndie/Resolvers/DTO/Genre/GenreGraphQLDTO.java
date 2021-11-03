package com.IndieAn.GoFundIndie.Resolvers.DTO.Genre;

import com.IndieAn.GoFundIndie.Domain.Entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class GenreGraphQLDTO {
    private long id;
    private String name;

    public GenreGraphQLDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static GenreGraphQLDTO from(Genre genre) {
        return GenreGraphQLDTO.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }

    public static Genre to(GenreGraphQLDTO dto) {
        if(dto.getName() == null) return null;
        Genre genre = new Genre();
        genre.setName(dto.getName());
        return genre;
    }
}