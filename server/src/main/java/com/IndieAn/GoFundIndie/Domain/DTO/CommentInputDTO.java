package com.IndieAn.GoFundIndie.Domain.DTO;

public class CommentInputDTO {
    private int rating;
    private long boardId;
    private int donation;
    private String commentBody;
    private boolean spoiler;

    public boolean isSpoiler() {
        return spoiler;
    }

    public int getRating() {
        return rating;
    }

    public long getBoardId() {
        return boardId;
    }

    public int getDonation() {
        return donation;
    }

    public String getCommentBody() {
        return commentBody;
    }
}
