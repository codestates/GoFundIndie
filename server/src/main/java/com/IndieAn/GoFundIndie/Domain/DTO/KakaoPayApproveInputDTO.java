package com.IndieAn.GoFundIndie.Domain.DTO;

import lombok.Data;

@Data
public class KakaoPayApproveInputDTO {
    private Integer amount;
    private String tid;
    private String pg_token;


    public Integer getAmount() {
        return amount;
    }

    public String getTid() {
        return tid;
    }

    public String getPg_token() {
        return pg_token;
    }
}
