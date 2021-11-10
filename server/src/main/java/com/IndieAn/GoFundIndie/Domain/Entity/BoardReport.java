package com.IndieAn.GoFundIndie.Domain.Entity;

import lombok.Builder;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
public class BoardReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @ManyToOne(targetEntity = Board.class)
    @JoinColumn(name = "board_id", nullable = false)
    private Board boardId;

    @Column(nullable = false)
    private String body;

    public BoardReport() {}

    @Builder
    public BoardReport(long id, User userId, Board boardId, String body) {
        Assert.notNull(userId, "userId not null");
        Assert.notNull(boardId, "boardId not null");
        Assert.notNull(body, "body not null");

        this.id = id;
        this.userId = userId;
        this.boardId = boardId;
        this.body = body;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Board getBoardId() {
        return boardId;
    }

    public void setBoardId(Board boardId) {
        this.boardId = boardId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
