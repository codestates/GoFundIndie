package com.IndieAn.GoFundIndie.Resolvers.DTO.BoardReport;

import lombok.Builder;

import java.util.List;

public class WrappingBoardReportsGraphqlDTO {
    private int code;
    private List<BoardReportGraphQLDTO> data;

    @Builder
    public WrappingBoardReportsGraphqlDTO(int code, List<BoardReportGraphQLDTO> data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public List<BoardReportGraphQLDTO> getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<BoardReportGraphQLDTO> data) {
        this.data = data;
    }

    public static WrappingBoardReportsGraphqlDTO bad(int code) {
        return WrappingBoardReportsGraphqlDTO.builder()
                .code(code)
                .build();
    }
}
