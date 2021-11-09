package com.IndieAn.GoFundIndie.Resolvers.Mutations;

import com.IndieAn.GoFundIndie.Domain.Entity.Genre;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.GenreRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.OnlyCodeDTO;
import com.IndieAn.GoFundIndie.Service.GqlUserValidService;
import graphql.schema.DataFetchingEnvironment;
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

    private final GqlUserValidService gqlUserValidService;

    public OnlyCodeDTO CreateGenre(GenreGraphQLDTO dto, DataFetchingEnvironment env) {
        int code = gqlUserValidService.envValidCheck(env);

        if(code == 0) {
            if(!gqlUserValidService.findUser(env).isAdminRole())
                return OnlyCodeDTO.builder().code(4300).build();

            Genre genre = GenreGraphQLDTO.to(dto);

            if(genre == null)
                return OnlyCodeDTO.builder().code(4006).build();

            genreRepository.RegisterDatabase(genre);
            return OnlyCodeDTO.builder().code(2000).build();
        } else {
            return OnlyCodeDTO.builder().code(code).build();
        }
    }

    public OnlyCodeDTO DeleteGenreId(Long id, DataFetchingEnvironment env) {
        int code = gqlUserValidService.envValidCheck(env);

        if(code == 0) {
            if(!gqlUserValidService.findUser(env).isAdminRole())
                return OnlyCodeDTO.builder().code(4300).build();

            if(!genreRepository.Delete(id))
                return OnlyCodeDTO.builder().code(4404).build();

            return OnlyCodeDTO.builder().code(2000).build();
        } else {
            return OnlyCodeDTO.builder().code(code).build();
        }
    }
}
