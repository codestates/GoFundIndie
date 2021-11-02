package com.IndieAn.GoFundIndie.Resolvers;

import com.IndieAn.GoFundIndie.Domain.Entity.Genre;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Repository.GenreRepository;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import com.IndieAn.GoFundIndie.Resolvers.Board.TempBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.Genre.GenreGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.User.UserGraphQLDTO;
import graphql.kickstart.execution.context.GraphQLContext;
import graphql.kickstart.servlet.context.GraphQLServletContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Mutation implements GraphQLMutationResolver {

     // Test
     private final EntityManager em;

     private final GenreRepository genreRepository;
     private final UserRepository userRepository;
     private final BoardRepository boardRepository;

     private String TOKEN;

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
    public TempBoardDTO CreateTempBoard(Long id, DataFetchingEnvironment env) {
        try {
            GraphQLServletContext context = env.getContext();
            HttpServletRequest request = context.getHttpServletRequest();
            TOKEN = request.getHeader("accesstoken");
//            log.info(d);
            User user = userRepository.FindUserByIdDB(id);
            long boardId = boardRepository.RegisterTempBoard(user);
            return TempBoardDTO.builder()
                    .id(boardId).code(2000).build();
        } catch (NullPointerException e) {
            return TempBoardDTO.builder()
                    .code(4000).build();
        }
    }
}
