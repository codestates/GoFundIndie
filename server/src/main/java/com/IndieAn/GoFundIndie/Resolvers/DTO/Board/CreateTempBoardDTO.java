package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;

@Builder
public class CreateTempBoardDTO {
    private long id;

    public CreateTempBoardDTO() {}

    public CreateTempBoardDTO(long id) { this.id = id; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
