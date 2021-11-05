package com.IndieAn.GoFundIndie.Resolvers.DTO.BoardGenre;

import lombok.Builder;

@Builder
public class LinkBoardGenreDTO {
    private long boardId;
    private long genreId;

    public LinkBoardGenreDTO() {}

    public LinkBoardGenreDTO(long boardId, long genreId) {
        this.boardId = boardId;
        this.genreId = genreId;
    }

    public long getBoardId() {
        return boardId;
    }

    public void setBoardId(long boardId) {
        this.boardId = boardId;
    }

    public long getGenreId() {
        return genreId;
    }

    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }
}
