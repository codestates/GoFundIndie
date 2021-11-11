package com.IndieAn.GoFundIndie.Domain.DTO;

import lombok.Data;

@Data
public class KakaoPayApproveInputDTO {
    private String pg_token;
    private long boardId;

    public long getBoardId() {
        return boardId;
    }

    public String getPg_token() {
        return pg_token;
    }
}
