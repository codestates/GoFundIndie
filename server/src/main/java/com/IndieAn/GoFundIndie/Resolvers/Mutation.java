package com.IndieAn.GoFundIndie.Resolvers;

import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.CreateBoardCompleteDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.WrappingCreateBoardCompleteDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.WrappingCreateTempBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.BoardGenre.WrappingLinkBoardGenreDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.CreateCastingCompleteDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.PutCastingDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.WrappingCastingGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.WrappingCreateTempCastingDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.OnlyCodeDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.User.UserGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.Mutations.*;
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
    private final CastingMutation castingMutation;
    private final GenreMutation genreMutation;
    private final UserMutation userMutation;
    private final BoardGenreMutation boardGenreMutation;

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

    public OnlyCodeDTO ApproveBoard(long id, DataFetchingEnvironment env) {
        return boardMutation.ApproveBoard(id, env);
    }

    // ---- Casting ----
    //

    // 캐스팅 임시
    public WrappingCreateTempCastingDTO CreateTempCasting(long id, DataFetchingEnvironment env) {
        return castingMutation.CreateTempCasting(id,env);
    }

    // 캐스팅 등록
    public WrappingCreateTempCastingDTO CompleteCasting(CreateCastingCompleteDTO dto, DataFetchingEnvironment env) {
        return castingMutation.CompleteCasting(dto, env);
    }

    // 캐스팅 수정
    public WrappingCreateTempCastingDTO PutCasting(PutCastingDTO dto, DataFetchingEnvironment env) {
        return castingMutation.PutCasting(dto, env);
    }

    // 캐스팅 삭제
    public OnlyCodeDTO DeleteCasting(long id, DataFetchingEnvironment env) {
        return castingMutation.DeleteCasting(id, env);
    }

    // ---- BoardGenre ----
    //

    public WrappingLinkBoardGenreDTO LinkBoardGenre(Long boardId, Long genreId, DataFetchingEnvironment env) {
        return boardGenreMutation.LinkBoardGenre(boardId, genreId, true, env);
    }

    public WrappingLinkBoardGenreDTO DisLinkBoardGenre(Long boardId, Long genreId, DataFetchingEnvironment env) {
        return boardGenreMutation.LinkBoardGenre(boardId, genreId, false, env);
    }
}
