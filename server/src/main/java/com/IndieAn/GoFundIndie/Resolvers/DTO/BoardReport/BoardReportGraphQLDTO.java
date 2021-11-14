package com.IndieAn.GoFundIndie.Resolvers.DTO.BoardReport;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.BoardReport;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import lombok.Builder;
import org.springframework.util.Assert;

public class BoardReportGraphQLDTO {
    private Long id;
    private Long boardId;
    private String boardTitle;
    private Long reporterId;
    private String reporterNickname;
    private Long defendantId;
    private String defendantNickname;
    private String body;

    @Builder
    public BoardReportGraphQLDTO(Long id, Long boardId, String boardTitle, Long reporterId, String reporterNickname, Long defendantId, String defendantNickname, String body) {
        Assert.notNull(id, "id not null");
        Assert.notNull(boardId, "boardId not null");
        Assert.notNull(boardTitle, "boardTitle not null");
        Assert.notNull(reporterId, "reporterId not null");
        Assert.notNull(reporterNickname, "reporterNickname not null");
        Assert.notNull(defendantId, "defendantId not null");
        Assert.notNull(defendantNickname, "defendantNickname not null");
        Assert.notNull(body, "body not null");

        this.id = id;
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.reporterId = reporterId;
        this.reporterNickname = reporterNickname;
        this.defendantId = defendantId;
        this.defendantNickname = defendantNickname;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public String getReporterNickname() {
        return reporterNickname;
    }

    public void setReporterNickname(String reporterNickname) {
        this.reporterNickname = reporterNickname;
    }

    public Long getDefendantId() {
        return defendantId;
    }

    public void setDefendantId(Long defendantId) {
        this.defendantId = defendantId;
    }

    public String getDefendantNickname() {
        return defendantNickname;
    }

    public void setDefendantNickname(String defendantNickname) {
        this.defendantNickname = defendantNickname;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static BoardReportGraphQLDTO from(BoardReport en) {
        User reporter = en.getUserId();
        User defendant = en.getBoardId().getUserId();
        Board board = en.getBoardId();

        return BoardReportGraphQLDTO.builder()
                .id(en.getId())
                .boardId(board.getId())
                .boardTitle(board.getTitle())
                .reporterId(reporter.getId())
                .reporterNickname(reporter.getNickname())
                .defendantId(defendant.getId())
                .defendantNickname(defendant.getNickname())
                .body(en.getBody())
                .build();
    }
}
