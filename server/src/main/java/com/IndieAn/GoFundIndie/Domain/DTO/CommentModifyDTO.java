package com.IndieAn.GoFundIndie.Domain.DTO;

public class CommentModifyDTO {
    private Integer rating;
    private long boardId;
    private String commentBody;
    private boolean spoiler;

    public boolean isSpoiler() {
        return spoiler;
    }

    public Integer getRating() {
        return rating;
    }

    public long getBoardId() {
        return boardId;
    }

    public String getCommentBody() {
        return commentBody;
    }

}
