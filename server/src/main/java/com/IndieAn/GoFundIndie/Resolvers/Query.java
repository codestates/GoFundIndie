package com.IndieAn.GoFundIndie.Resolvers;

import com.IndieAn.GoFundIndie.Common.SearchTypes;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Repository.GenreRepository;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.BoardGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.ViewBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.WrappingViewBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.WrappingViewBoardsDTO;
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
    public WrappingViewBoardDTO FindBoardId(Long id) {
        try {
            return WrappingViewBoardDTO.builder()
                    .code(2000)
                    .data(ViewBoardDTO.from(boardRepository.findBoardId(id)))
                    .build();
        } catch (NullPointerException e) {
            return WrappingViewBoardDTO.builder()
                    .code(4401)
                    .build();
        }
    }

    public WrappingViewBoardsDTO FindBoards() {
        return WrappingViewBoardsDTO.builder()
                .code(2000)
                .data(boardRepository.findBoards().stream()
                        .map(BoardGraphQLDTO::from)
                        .collect(Collectors.toList()))
                .build();
    }

    // type

    //   - Genre = 장르별 영화
    //   - My = 내가 찜한 영화
    public String FindBoardsType(String type) {
        try {
            SearchTypes searchType = SearchTypes.findSearchType(type);
            return searchType.name();
        } catch (RuntimeException e) {
            return "enum type null";
        }
    }
}
