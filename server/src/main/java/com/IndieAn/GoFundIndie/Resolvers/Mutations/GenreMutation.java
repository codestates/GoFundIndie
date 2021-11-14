package com.IndieAn.GoFundIndie.Resolvers.Mutations;

import com.IndieAn.GoFundIndie.Domain.Entity.Genre;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.GenreRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.GqlResponseCodeDTO;
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

    public GqlResponseCodeDTO CreateGenre(GenreGraphQLDTO dto, DataFetchingEnvironment env) {
        int code = gqlUserValidService.envValidCheck(env);

        if(code == 0) {
            User user = gqlUserValidService.findUser(env);
            if(user == null) return GqlResponseCodeDTO.bad(4400);
            else if(!user.isAdminRole())
                return GqlResponseCodeDTO.bad(4300);

            Genre genre = GenreGraphQLDTO.to(dto);

            if(genre == null)
                return GqlResponseCodeDTO.bad(4006);

            genreRepository.RegisterDatabase(genre);
            return GqlResponseCodeDTO.ok();
        } else {
            return GqlResponseCodeDTO.bad(code);
        }
    }

    public GqlResponseCodeDTO DeleteGenreId(Long id, DataFetchingEnvironment env) {
        int code = gqlUserValidService.envValidCheck(env);

        if(code == 0) {
            User user = gqlUserValidService.findUser(env);
            if(user == null) return GqlResponseCodeDTO.bad(4400);
            else if(!user.isAdminRole())
                return GqlResponseCodeDTO.bad(4300);

            if(!genreRepository.Delete(id))
                return GqlResponseCodeDTO.bad(4404);

            return GqlResponseCodeDTO.ok();
        } else {
            return GqlResponseCodeDTO.bad(code);
        }
    }
}
