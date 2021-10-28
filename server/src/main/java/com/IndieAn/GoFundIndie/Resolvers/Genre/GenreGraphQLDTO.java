package com.IndieAn.GoFundIndie.Resolvers.Genre;

public class GenreGraphQLDTO {
    private String name;

    public GenreGraphQLDTO() {}

    public GenreGraphQLDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
