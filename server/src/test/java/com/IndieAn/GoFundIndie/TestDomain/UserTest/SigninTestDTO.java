package com.IndieAn.GoFundIndie.TestDomain.UserTest;

public class SigninTestDTO {
    private int code;
    private AccessTokenTestDTO data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public AccessTokenTestDTO getData() {
        return data;
    }

    public void setData(AccessTokenTestDTO data) {
        this.data = data;
    }
}
