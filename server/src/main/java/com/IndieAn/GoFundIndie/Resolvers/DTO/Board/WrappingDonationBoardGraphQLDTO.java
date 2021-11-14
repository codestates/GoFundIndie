package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class WrappingDonationBoardGraphQLDTO {

    @NotBlank
    private int code;

    private List<DonationBoardGraphQLDTO> data; // nullable

    public WrappingDonationBoardGraphQLDTO() {}

    @Builder
    public WrappingDonationBoardGraphQLDTO(int code, List<DonationBoardGraphQLDTO> data) {
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
