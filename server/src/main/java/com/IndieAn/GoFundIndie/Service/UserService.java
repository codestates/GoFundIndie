package com.IndieAn.GoFundIndie.Service;

import com.IndieAn.GoFundIndie.Domain.DTO.UserSIgnInDTO;
import com.IndieAn.GoFundIndie.Domain.DTO.UserSignUpDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Value("#{info['gofundindie.signkey']}")
    private String SIGN_KEY;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 유저 정보를 받아 회원가입을 진행하는 서비스 기능
    public User CreateUserData(UserSignUpDTO userSignUpDTO) {
        // DB > User table에 유저정보를 저장한다.
        // 같은 email이 존재하면 null을 리턴
        if(userRepository.FindUserByEmail(userSignUpDTO.getEmail()) != null) return null;
        return userRepository.CreateUser(userSignUpDTO);
    }

    // 로그인 정보를 기준으로 email과 password를 확인해 유저정보를 찾는다
    public User FindUser(UserSIgnInDTO userSIgnInDTO) {
        User user = userRepository.FindUserByEmail(userSIgnInDTO.getEmail());
        // user가 없거나 비밀번호가 틀렸다면 null을 리턴한다.
        if(user == null || !user.getPassword().equals(userSIgnInDTO.getPassword())) {
            return null;
        }

        return user;
    }

    // AccessToken과 RefreshToken을 만드는 작업을 수행한다
    public String CreateToken(User user, Long time) {
        System.out.println(SIGN_KEY);
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer("fresh")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofSeconds(time).toMillis()))
                .claim("email", user.getEmail())
                .claim("password", user.getPassword())
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY)
                .compact();
    }
}
