package com.IndieAn.GoFundIndie.Domain.Entity;

import javax.persistence.*;

@Entity
public class CommentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @ManyToOne(targetEntity = Comment.class)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment commentId;

    @Column(nullable = false)
    private String body;

    public CommentReport() {}

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

    public Comment getCommentId() {
        return commentId;
    }

    public void setCommentId(Comment commentId) {
        this.commentId = commentId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
