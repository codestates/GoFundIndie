package com.IndieAn.GoFundIndie.Resolvers;

import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.CreateBoardCompleteDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.PutBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.WrappingCreateTempBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.BoardGenre.WrappingLinkBoardGenreDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.BoardReport.CreateBoardReportDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.CreateCastingCompleteDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.PutCastingDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.WrappingCreateTempCastingDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.GqlResponseCodeDTO;
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
    private final BoardLikeMutation boardLikeMutation;
    private final BoardReportMutation boardReportMutation;

    // ---- USER ----
    //
    public int CreateUser(UserGraphQLDTO dto) {
        return userMutation.CreateUser(dto);
    }

    // ---- GENRE ----
    //
    public GqlResponseCodeDTO CreateGenre(GenreGraphQLDTO dto, DataFetchingEnvironment env) {
        return genreMutation.CreateGenre(dto, env);
    }

    public GqlResponseCodeDTO DeleteGenreId(Long id, DataFetchingEnvironment env) {
        return genreMutation.DeleteGenreId(id, env);
    }

    // ---- BOARD ----
    //
    public WrappingCreateTempBoardDTO CreateTempBoard(DataFetchingEnvironment env) {
        return boardMutation.CreateTempBoard(env);
    }

    public WrappingCreateTempBoardDTO CompleteBoard(CreateBoardCompleteDTO dto, DataFetchingEnvironment env) {
        return boardMutation.CompleteBoard(dto, env);
    }

    public WrappingCreateTempBoardDTO PutBoard(PutBoardDTO dto, DataFetchingEnvironment env) {
        return boardMutation.PutBoard(dto, env);
    }

    public GqlResponseCodeDTO DeleteBoard(long id, DataFetchingEnvironment env) {
        return boardMutation.DeleteBoard(id, env);
    }

    public GqlResponseCodeDTO ApproveBoard(long id, DataFetchingEnvironment env) {
        return boardMutation.ApproveBoard(id, true, env);
    }

    public GqlResponseCodeDTO DisapproveBoard(long id, DataFetchingEnvironment env) {
        return boardMutation.ApproveBoard(id, false, env);
    }

    public GqlResponseCodeDTO SwitchLikeBoard(long boardId, DataFetchingEnvironment env) {
        return boardLikeMutation.SwitchLikeBoard(boardId, env);
    }

    // ---- Casting ----
    //

    public WrappingCreateTempCastingDTO CreateTempCasting(long id, DataFetchingEnvironment env) {
        return castingMutation.CreateTempCasting(id,env);
    }

    public WrappingCreateTempCastingDTO CompleteCasting(CreateCastingCompleteDTO dto, DataFetchingEnvironment env) {
        return castingMutation.CompleteCasting(dto, env);
    }

    public WrappingCreateTempCastingDTO PutCasting(PutCastingDTO dto, DataFetchingEnvironment env) {
        return castingMutation.PutCasting(dto, env);
    }

    public GqlResponseCodeDTO DeleteCasting(long id, DataFetchingEnvironment env) {
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

    // ---- BoardReport ----
    //

    public GqlResponseCodeDTO ReportBoard(CreateBoardReportDTO dto, DataFetchingEnvironment env) {
        return boardReportMutation.ReportBoard(dto, env);
    }

    public GqlResponseCodeDTO DeleteReport(Long id, DataFetchingEnvironment env) {
        return boardReportMutation.DeleteReport(id, env);
    }
}
