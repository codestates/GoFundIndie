package com.IndieAn.GoFundIndie.Resolvers.Mutations;

import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Resolvers.DTO.User.UserGraphQLDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserMutation {
    //Test
    private final EntityManager em;

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
}
