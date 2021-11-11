package com.IndieAn.GoFundIndie.Resolvers.DTO.BoardReport;

import lombok.Builder;
import org.springframework.util.Assert;

public class CreateBoardReportDTO {
    private Long boardId;
    private String body;

    public CreateBoardReportDTO() {}

    @Builder
    public CreateBoardReportDTO(Long boardId, String body) {
        Assert.notNull(boardId, "boardId not null");
        Assert.notNull(body, "body not null");

        this.boardId = boardId;
        this.body = body;
    }

    public Long getBoardId() {
        return boardId;
    }

    public String getBody() {
        return body;
    }
}
