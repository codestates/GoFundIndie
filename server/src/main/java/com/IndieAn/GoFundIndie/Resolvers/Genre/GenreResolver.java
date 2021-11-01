package com.IndieAn.GoFundIndie.Resolvers.Genre;

import com.IndieAn.GoFundIndie.Repository.GenreRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GenreResolver implements GraphQLQueryResolver {

    private final GenreRepository genreRepository;

    public GenreGraphQLDTO FindGenreId(Long id) {
        return GenreGraphQLDTO.from(genreRepository.FindById(id));
    }

    public List<GenreGraphQLDTO> FindAllGenre() {
        return genreRepository.FindAll().stream()
                .map(el -> GenreGraphQLDTO.from(el))
                .collect(Collectors.toList());
    }
}
