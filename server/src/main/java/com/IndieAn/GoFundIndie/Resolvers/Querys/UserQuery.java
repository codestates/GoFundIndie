package com.IndieAn.GoFundIndie.Resolvers.Querys;

import com.IndieAn.GoFundIndie.Repository.UserRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.User.UserGraphQLDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserQuery {
    private final UserRepository userRepository;

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
}
