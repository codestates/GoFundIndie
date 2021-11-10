package com.IndieAn.GoFundIndie.Resolvers;

import com.IndieAn.GoFundIndie.Common.SearchTypes;
import com.IndieAn.GoFundIndie.Repository.CommentRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.*;
import com.IndieAn.GoFundIndie.Resolvers.DTO.BoardReport.WrappingBoardReportGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.BoardReport.WrappingBoardReportsGraphqlDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Comment.CommentGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.User.UserGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.Querys.BoardQuery;
import com.IndieAn.GoFundIndie.Resolvers.Querys.BoardReportQuery;
import com.IndieAn.GoFundIndie.Resolvers.Querys.GenreQuery;
import com.IndieAn.GoFundIndie.Resolvers.Querys.UserQuery;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Query implements GraphQLQueryResolver {
    private final BoardQuery boardQuery;
    private final GenreQuery genreQuery;
    private final UserQuery userQuery;
    private final BoardReportQuery boardReportQuery;

    private final CommentRepository cr;

    private final int limitMax = 100000000;

    // ---- USER ----
    //
    public UserGraphQLDTO FindUserId(Long id) {
        return userQuery.FindUserId(id);
    }

    public List<UserGraphQLDTO> FindAllUser() {
        return userQuery.FindAllUser();
    }


    // ---- GENRE ----
    //
    public GenreGraphQLDTO FindGenreId(Long id) {
        return genreQuery.FindGenreId(id);
    }

    public List<GenreGraphQLDTO> FindAllGenre() {
        return genreQuery.FindAllGenre();
    }

    // ---- BOARD ----
    //
    public WrappingViewBoardDTO FindBoardId(Long id, DataFetchingEnvironment env) {
        return boardQuery.FindBoardId(id, env);
    }

    public WrappingAdminViewBoardDTO FindBoardIdAdmin(Long id, DataFetchingEnvironment env) {
        return boardQuery.FindBoardIdAdmin(id, env);
    }

    public WrappingBoardGraphQLsDTO FindBoards(SearchTypes type, Integer limit, DataFetchingEnvironment env) {
        return boardQuery.FindBoards(type, Objects.requireNonNullElse(limit, limitMax), env);
    }

    public List<CommentGraphQLDTO> CommentTest(long id, int limit){
        return cr.findCommentByBoard(id,limit);
    }

    public WrappingDonationBoardGraphQLDTO FindDonationBoards(Integer limit, DataFetchingEnvironment env) {
        return boardQuery.FindDonationBoards(Objects.requireNonNullElse(limit, limitMax), env);
    }

    public WrappingLikeBoardGraphQLDTO FindLikeBoards(Integer limit, DataFetchingEnvironment env) {
        return boardQuery.FindLikeBoards(Objects.requireNonNullElse(limit, limitMax), env);
    }

    public WrappingRandomBoardsDTO FindRandomBoard(Integer limit, DataFetchingEnvironment env) {
        return boardQuery.FindRandomBoard(Objects.requireNonNullElse(limit, limitMax), env);
    }

    // ---- BOARD REPORT ----
    //
    public WrappingBoardReportGraphQLDTO FindBoardReport(Long id, DataFetchingEnvironment env) {
        return boardReportQuery.FindBoardReport(id, env);
    }

    public WrappingBoardReportsGraphqlDTO FindBoardReports(DataFetchingEnvironment env) {
        return boardReportQuery.FindBoardReports(env);
    }
}
