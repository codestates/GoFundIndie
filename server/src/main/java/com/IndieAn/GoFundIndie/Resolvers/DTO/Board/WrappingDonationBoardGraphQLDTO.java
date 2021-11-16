package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;
import org.springframework.util.Assert;
import java.util.List;

public class WrappingDonationBoardGraphQLDTO {
    private int code;

    private List<DonationBoardGraphQLDTO> data; // nullable

    public WrappingDonationBoardGraphQLDTO() {}

    @Builder
    public WrappingDonationBoardGraphQLDTO(Integer code, List<DonationBoardGraphQLDTO> data) {
        Assert.notNull(code, "code is not null");

        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DonationBoardGraphQLDTO> getData() {
        return data;
    }

    public void setData(List<DonationBoardGraphQLDTO> data) {
        this.data = data;
    }
}
