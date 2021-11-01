package com.IndieAn.GoFundIndie.Resolvers.User;

import com.IndieAn.GoFundIndie.Domain.Entity.User;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserMutation implements GraphQLMutationResolver {

    // 이것도 UserRepository 에서 해야 하는건데 예제라 그냥 여기에 썼습니다
     private final EntityManager em;

    // 신규 유저 생성
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

    // 유저 정보 변경
}
