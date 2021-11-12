package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;
import org.springframework.util.Assert;

import java.util.List;

public class BoardPagingDTO {
    private Integer countBoards;
    private Integer countPages;
    private Integer boardsPerPage;
    private Integer currentPage;
    private List<BoardGraphQLDTO> content;

    public BoardPagingDTO() {}

    @Builder
    public BoardPagingDTO(Integer countBoards, Integer countPages, Integer boardsPerPage, Integer currentPage, List<BoardGraphQLDTO> content) {
        Assert.notNull(countBoards, "countBoards not null");
        Assert.notNull(countPages, "countPages not null");
        Assert.notNull(boardsPerPage, "boardsPerPage not null");
        Assert.notNull(currentPage, "currentPage not null");

        this.countBoards = countBoards;
        this.countPages = countPages;
        this.boardsPerPage = boardsPerPage;
        this.currentPage = currentPage;
        this.content = content;
    }

    public Integer getCountBoards() {
        return countBoards;
    }

    public void setCountBoards(Integer countBoards) {
        this.countBoards = countBoards;
    }

    public Integer getCountPages() {
        return countPages;
    }

    public void setCountPages(Integer countPages) {
        this.countPages = countPages;
    }

    public Integer getBoardsPerPage() {
        return boardsPerPage;
    }

    public void setBoardsPerPage(Integer boardsPerPage) {
        this.boardsPerPage = boardsPerPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<BoardGraphQLDTO> getContent() {
        return content;
    }

    public void setContent(List<BoardGraphQLDTO> content) {
        this.content = content;
    }
}
