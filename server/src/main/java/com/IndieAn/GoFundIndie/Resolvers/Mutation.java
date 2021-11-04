package com.IndieAn.GoFundIndie.Resolvers;

import com.IndieAn.GoFundIndie.Domain.Entity.Genre;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Repository.GenreRepository;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.CreateBoardCompleteDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.WrappingCreateTempBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.CreateTempBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.User.UserGraphQLDTO;
import com.IndieAn.GoFundIndie.Service.UserService;
import graphql.kickstart.servlet.context.GraphQLServletContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Mutation implements GraphQLMutationResolver {
    // Test
    private final EntityManager em;

    private final GenreRepository genreRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    private String accessToken = null;
    private Map<String, Object> checkToken = null;

    private GraphQLServletContext context = null;
    private HttpServletRequest request = null;

    //
    //TODO ---- USER ----
    //
    public int CreateUser(UserGraphQLDTO dto) {
        User user = User.from(dto);

        if(user == null) {
            // user 구성의 필수 정보가 빠짐
            return 4000;
        } else {
            em.persist(user);
            em.flush();
            em.close();
            // Success
            return 2000;
        }
    }

    //
    //TODO ---- GENRE ----
    //
    public int CreateGenre(GenreGraphQLDTO dto) {
        Genre genre = GenreGraphQLDTO.to(dto);

        if(genre == null) {
            return 4000;
        } else {
            genreRepository.RegisterDatabase(genre);
            return 2000;
        }
    }

    public int DeleteGenreId(Long id) {
        if(genreRepository.Delete(id)) return 2000;
        else return 4000;
    }

    //
    //TODO ---- BOARD ----
    //
    public WrappingCreateTempBoardDTO CreateTempBoard(DataFetchingEnvironment env) {
        try {
            context = env.getContext();
            request = context.getHttpServletRequest();
            accessToken = request.getHeader("accesstoken");

            // No token in the Header : 4000
            if(context == null || request == null || accessToken == null)
                return WrappingCreateTempBoardDTO.builder().code(4000).build();

            checkToken = userService.CheckToken(accessToken);

            if(checkToken.get("email") != null) {
                long boardId = boardRepository.RegisterTempBoard(
                        userService.FindUserUseEmail(checkToken.get("email").toString()));
                return WrappingCreateTempBoardDTO.builder().code(2000)
                        .data(CreateTempBoardDTO.builder().id(boardId).build())
                        .build();
            } else {
                // Token Invalid
                return WrappingCreateTempBoardDTO.builder()
                        .code(Integer.parseInt(checkToken.get("code").toString())).build();
            }
            // Test Code : No Access Token
//            User user = userRepository.FindUserByIdDB(1L);
//            long boardId = boardRepository.RegisterTempBoard(user);
//            return CreateTempBoardResponseDTO.builder().code(2000).data(
//                    TempBoardDTO.builder().id(boardId).build()
//            ).build();
        } catch (NullPointerException e) {
            return WrappingCreateTempBoardDTO.builder().code(4000).build();
        }
    }

    public WrappingCreateTempBoardDTO CompleteBoard(CreateBoardCompleteDTO dto, DataFetchingEnvironment env) {
        try {
            context = env.getContext();
            request = context.getHttpServletRequest();
            accessToken = request.getHeader("accesstoken");

            // No token in the Header : 4000
            if(context == null || request == null || accessToken == null)
                return WrappingCreateTempBoardDTO.builder().code(4000).build();

            checkToken = userService.CheckToken(accessToken);

            if(checkToken.get("email") != null) {
                long userId = userService.FindUserUseEmail(checkToken.get("email").toString()).getId();

                // Invalid UserId
                if(userId != dto.getUserId())
                    return WrappingCreateTempBoardDTO.builder().code(4301).build();

                return null;
            } else {
                // Token Invalid
                return WrappingCreateTempBoardDTO.builder()
                        .code(Integer.parseInt(checkToken.get("code").toString()))
                        .build();
            }
        } catch (NullPointerException e) {
            return WrappingCreateTempBoardDTO.builder().code(4000).build();
        }
    }
}
