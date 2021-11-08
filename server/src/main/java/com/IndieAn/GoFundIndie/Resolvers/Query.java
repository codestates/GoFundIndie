package com.IndieAn.GoFundIndie.Resolvers;

import com.IndieAn.GoFundIndie.Common.SearchTypes;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.WrappingAdminViewBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.WrappingBoardGraphQLsDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.WrappingViewBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.User.UserGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.Querys.BoardQuery;
import com.IndieAn.GoFundIndie.Resolvers.Querys.GenreQuery;
import com.IndieAn.GoFundIndie.Resolvers.Querys.UserQuery;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Query implements GraphQLQueryResolver {
    private final BoardQuery boardQuery;
    private final GenreQuery genreQuery;
    private final UserQuery userQuery;

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
    public WrappingViewBoardDTO FindBoardId(Long id) {
        return boardQuery.FindBoardId(id);
    }

    public WrappingAdminViewBoardDTO FindBoardIdAdmin(Long id, DataFetchingEnvironment env) {
        return boardQuery.FindBoardIdAdmin(id, env);
    }

    public WrappingBoardGraphQLsDTO FindBoards(SearchTypes type, Integer limit, DataFetchingEnvironment env) {
        if(limit == null) return boardQuery.FindBoards(type, 100000000, env);
        return boardQuery.FindBoards(type, limit, env);
    }
}
