package com.IndieAn.GoFundIndie.Resolvers;

import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Repository.GenreRepository;
import com.IndieAn.GoFundIndie.Resolvers.Board.BoardGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.Genre.GenreGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.User.UserGraphQLDTO;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class Query implements GraphQLQueryResolver {

    // 원래는 UserRepository 에서 하는게 맞는데 예제라서 그냥 여기에 썼습니다
    private final EntityManager em;

    private final GenreRepository genreRepository;
    private final BoardRepository boardRepository;

    //
    //TODO ---- USER ----
    //
    public UserGraphQLDTO FindUserId(Long id) {
        return UserGraphQLDTO.from(em.find(User.class, id));
    }

    public List<UserGraphQLDTO> FindAllUser() {
        List<UserGraphQLDTO> result = new ArrayList<>();
        em.createQuery("SELECT a FROM User a", User.class)
                .getResultList().forEach(el -> result.add(UserGraphQLDTO.from(el)));
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
                .map(el -> GenreGraphQLDTO.from(el))
                .collect(Collectors.toList());
    }

    //
    //TODO ---- BOARD ----
    //
    public BoardGraphQLDTO FindBoardId(Long id) {
        return BoardGraphQLDTO.from(boardRepository.findBoardId(id));
    }
}
