package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Domain.DTO.UserSIgnInDTO;
import com.IndieAn.GoFundIndie.Domain.DTO.UserSignUpDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
public class UserController {
    private final UserService userService;
    private final HashMap<String, Object> body = new HashMap<>();
    // accessToken 유효 시간
    private final static Long ACCESS_TIME = 1000 * 60 * 60 * 24L;
    // refreshToken 유효 시간
    private final static Long REFRESH_TIME = 1000 * 60 * 60 * 180L;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "signup")
    public ResponseEntity<?> UserSignUp(@RequestBody UserSignUpDTO  userSignUpDTO) {
        try {
            body.clear();
            // DB에 해당 email이 존재하는지 확인한다. 반환 값이 null이라면 이미 존재하는 email이다.
            User user = userService.CreateUserData(userSignUpDTO);

            if(user == null) {
                body.put("message", "올바른 요청이 아닙니다.");
                return ResponseEntity.badRequest().body(body);
            }

            return ResponseEntity.ok().body("");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @PostMapping(value = "signin")
    public ResponseEntity<?> UserLogin(@RequestBody UserSIgnInDTO userSIgnInDTO, HttpServletResponse response) {
        try {
            body.clear();
            // id와 password 를 기준으로 DB에 일치하는 유저 데이터를 불러온다.
            // 유저 데이터에 email과 password를 토큰에 담아 accesstoken과 refreshToken을 생성한다.
            // refreshToken은 쿠키(key -> refreshToken)에 담겨 전달한다.
            User user = userService.FindUser(userSIgnInDTO);

            if(user == null) {
                body.put("message", "email이나 비밀번호가 올바르지 않습니다");
                return ResponseEntity.badRequest().body(body);
            }
            else {
                // 유저가 DB에 존재한다면 accessToken과 refreshToken을 발급하여 body에 내보내준다.
                Cookie cookie = new Cookie("refreshToken", userService.CreateToken(user, REFRESH_TIME));
                response.addCookie(cookie);

                body.put("id", user.getId());
                body.put("accessToken", userService.CreateToken(user, ACCESS_TIME));
                body.put("refreshToken", cookie.getValue());
                return ResponseEntity.ok().body(body);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }
}
