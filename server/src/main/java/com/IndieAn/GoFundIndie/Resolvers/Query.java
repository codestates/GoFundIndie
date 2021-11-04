package com.IndieAn.GoFundIndie.Resolvers;

import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Repository.GenreRepository;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.*;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.User.UserGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.Querys.FindBoardsQuery;
import com.IndieAn.GoFundIndie.Service.UserService;
import graphql.kickstart.servlet.context.GraphQLServletContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Query implements GraphQLQueryResolver {
    private final GenreRepository genreRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    private final FindBoardsQuery findBoardsQuery;

    private String accessToken = null;
    private Map<String, Object> checkToken = null;

    private GraphQLServletContext context = null;
    private HttpServletRequest request = null;

    //
    //TODO ---- USER ----
    //
    public UserGraphQLDTO FindUserId(Long id) {
        return UserGraphQLDTO.from(
                userRepository.FindUserByIdDB(id)
        );
    }

    public List<UserGraphQLDTO> FindAllUser() {
        List<UserGraphQLDTO> result = new ArrayList<>();
        userRepository.FindUserList()
                .forEach(el -> result.add(UserGraphQLDTO.from(el)));
        return result;
    }


    //
    //TODO ---- GENRE ----
    //
    public GenreGraphQLDTO FindGenreId(Long id) {
        return GenreGraphQLDTO.from(genreRepository.FindById(id));
    }

    public List<GenreGraphQLDTO> FindAllGenre() {
        return genreRepository.FindAll().stream()
                .map(GenreGraphQLDTO::from)
                .collect(Collectors.toList());
    }

    //
    //TODO ---- BOARD ----
    //
    public WrappingViewBoardDTO FindBoardId(Long id) {
        return findBoardsQuery.FindBoardId(id);
    }

    public WrappingBoardGraphQLsDTO FindBoards(String type, DataFetchingEnvironment env) {
        return findBoardsQuery.FindBoards(type, env);
    }
}
