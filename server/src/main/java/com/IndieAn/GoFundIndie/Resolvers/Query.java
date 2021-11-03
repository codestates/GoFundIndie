package com.IndieAn.GoFundIndie.Resolvers;

import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Repository.GenreRepository;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.BoardGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.ViewBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.ViewWrappingBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.ViewWrappingBoardsDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.User.UserGraphQLDTO;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Query implements GraphQLQueryResolver {
    private final GenreRepository genreRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

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
    public ViewWrappingBoardDTO FindBoardId(Long id) {
        try {
            return ViewWrappingBoardDTO.builder()
                    .code(2000)
                    .data(ViewBoardDTO.from(boardRepository.findBoardId(id)))
                    .build();
        } catch (NullPointerException e) {
            return ViewWrappingBoardDTO.builder()
                    .code(4401)
                    .build();
        }
    }

    public ViewWrappingBoardsDTO FindBoards() {
        return ViewWrappingBoardsDTO.builder()
                .code(2000)
                .data(boardRepository.findBoards().stream()
                        .map(BoardGraphQLDTO::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
