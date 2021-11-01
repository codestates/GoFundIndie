package com.IndieAn.GoFundIndie.Resolvers.Board;

import lombok.Builder;

@Builder
public class TempBoardDTO {
    private long id;
    private int code;

    public TempBoardDTO() {}

    public TempBoardDTO(long id, int code) {
        this.id = id;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
