package com.IndieAn.GoFundIndie.Domain.Entity;

import javax.persistence.*;

@Entity
public class CommentRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "boolean default false")
    private boolean like;

    @Column(columnDefinition = "boolean default false")
    private boolean dislike;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @ManyToOne(targetEntity = Comment.class)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment commentId;

    public CommentRating() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isDislike() {
        return dislike;
    }

    public void setDislike(boolean dislike) {
        this.dislike = dislike;
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
}
