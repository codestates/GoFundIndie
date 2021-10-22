package com.IndieAn.GoFundIndie.Service;

import com.IndieAn.GoFundIndie.Domain.DTO.UserSignUpDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 유저 정보를 받아 회원가입을 진행하는 서비스 기능
    public User CreateUserData(UserSignUpDTO userSignUpDTO) {
        // DB > User table에 유저정보를 저장한다.
        // 같은 email이 존재하면 null을 리턴
        for(User u : userRepository.FindUserList()) {
            if(u.getEmail().equals(userSignUpDTO.getEmail())) {
                return null;
            }
        }
        return userRepository.CreateUser(userSignUpDTO);
    }
}
