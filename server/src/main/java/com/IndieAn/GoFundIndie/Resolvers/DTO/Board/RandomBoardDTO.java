package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;
import org.springframework.util.Assert;

import java.util.List;

public class RandomBoardDTO {
    private String phrase;
    private List<BoardGraphQLDTO> data;

    public RandomBoardDTO() {
    }

    @Builder
    public RandomBoardDTO(String phrase, List<BoardGraphQLDTO> data) {
        Assert.notNull(phrase, "phrase not null");

        this.phrase = phrase;
        this.data = data;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public List<BoardGraphQLDTO> getData() {
        return data;
    }

    public void setData(List<BoardGraphQLDTO> data) {
        this.data = data;
    }
}
