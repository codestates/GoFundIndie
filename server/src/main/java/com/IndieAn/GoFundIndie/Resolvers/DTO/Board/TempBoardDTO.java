package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;

@Builder
public class TempBoardDTO {
    private long id;

    public TempBoardDTO() {}

    public TempBoardDTO(long id) { this.id = id; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
