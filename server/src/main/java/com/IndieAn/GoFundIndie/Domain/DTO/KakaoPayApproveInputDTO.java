package com.IndieAn.GoFundIndie.Domain.DTO;

import lombok.Data;

@Data
public class KakaoPayApproveInputDTO {
    private String pg_token;

    public String getPg_token() {
        return pg_token;
    }
}
