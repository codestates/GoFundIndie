package com.IndieAn.GoFundIndie.Resolvers.DTO;

import lombok.Builder;

@Builder
public class OnlyCodeDTO {
    private int code;

    public OnlyCodeDTO() {}

    public OnlyCodeDTO(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
