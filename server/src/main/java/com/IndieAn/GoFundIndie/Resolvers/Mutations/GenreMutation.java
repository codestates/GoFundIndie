package com.IndieAn.GoFundIndie.Resolvers.Mutations;

import com.IndieAn.GoFundIndie.Domain.Entity.Genre;
import com.IndieAn.GoFundIndie.Repository.GenreRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class GenreMutation {
    private final GenreRepository genreRepository;

    //TODO : admin valid check

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
}
