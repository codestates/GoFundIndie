package com.IndieAn.GoFundIndie.Resolvers;

import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.CreateBoardCompleteDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.WrappingCreateTempBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.User.UserGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.Mutations.BoardMutation;
import com.IndieAn.GoFundIndie.Resolvers.Mutations.GenreMutation;
import com.IndieAn.GoFundIndie.Resolvers.Mutations.UserMutation;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Mutation implements GraphQLMutationResolver {
    private final BoardMutation boardMutation;
    private final GenreMutation genreMutation;
    private final UserMutation userMutation;

    // ---- USER ----
    //
    public int CreateUser(UserGraphQLDTO dto) {
        return userMutation.CreateUser(dto);
    }

    // ---- GENRE ----
    //
    public int CreateGenre(GenreGraphQLDTO dto) {
        return genreMutation.CreateGenre(dto);
    }

    public int DeleteGenreId(Long id) {
        return genreMutation.DeleteGenreId(id);
    }

    // ---- BOARD ----
    //
    public WrappingCreateTempBoardDTO CreateTempBoard(DataFetchingEnvironment env) {
        return boardMutation.CreateTempBoard(env);
    }

    public WrappingCreateTempBoardDTO CompleteBoard(CreateBoardCompleteDTO dto, DataFetchingEnvironment env) {
        return boardMutation.CompleteBoard(dto, env);
    }
}
