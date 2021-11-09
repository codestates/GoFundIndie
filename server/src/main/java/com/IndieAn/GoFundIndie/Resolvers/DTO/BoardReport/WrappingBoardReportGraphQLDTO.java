package com.IndieAn.GoFundIndie.Resolvers.DTO.BoardReport;

import lombok.Builder;

public class WrappingBoardReportGraphQLDTO {
    private int code;
    private BoardReportGraphQLDTO data;

    @Builder
    public WrappingBoardReportGraphQLDTO(int code, BoardReportGraphQLDTO data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public BoardReportGraphQLDTO getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(BoardReportGraphQLDTO data) {
        this.data = data;
    }

    public static WrappingBoardReportGraphQLDTO bad(int code) {
        return WrappingBoardReportGraphQLDTO.builder()
                .code(code)
                .build();
    }
}
