package com.IndieAn.GoFundIndie.Resolvers.DTO;

import lombok.Builder;

public class GqlResponseCodeIdDTO {
    private int code;
    private long id;

    @Builder
    public GqlResponseCodeIdDTO(int code, long id) {
        this.code = code;
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
