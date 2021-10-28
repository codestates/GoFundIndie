package com.IndieAn.GoFundIndie.Resolvers.User;

import com.IndieAn.GoFundIndie.Domain.Entity.User;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserResolver implements GraphQLQueryResolver {

    // 원래는 UserRepository 에서 하는게 맞는데 예제라서 그냥 여기에 썼습니다
    private final EntityManager em;

    public UserGraphQLDTO FindUserId(Long id) {
        return UserGraphQLDTO.from(em.find(User.class, id));
    }

    public List<UserGraphQLDTO> FindAllUser() {
        List<UserGraphQLDTO> result = new ArrayList<>();
        em.createQuery("SELECT a FROM User a", User.class)
                .getResultList().forEach(el -> result.add(UserGraphQLDTO.from(el)));
        return result;
    }
}
