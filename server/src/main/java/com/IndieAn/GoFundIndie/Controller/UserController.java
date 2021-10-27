package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Domain.DTO.UserModifyDTO;
import com.IndieAn.GoFundIndie.Domain.DTO.UserSIgnInDTO;
import com.IndieAn.GoFundIndie.Domain.DTO.UserSignUpDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.RefreshToken;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class UserController {
    private final UserService userService;
    private final HashMap<String, Object> body = new HashMap<>();
    private final HashMap<String, Object> data = new HashMap<>();
    // accessToken 유효 시간
    private final static Long ACCESS_TIME = 1000 * 60 * 30L;
    // refreshToken 유효 시간
    private final static Long REFRESH_TIME = 1000 * 60 * 60 * 24L;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> UserSignUp(@RequestBody UserSignUpDTO  userSignUpDTO) {
        try {
            body.clear();
            // DB에 해당 email이 존재하는지 확인한다. 반환 값이 null이라면 이미 존재하는 email이다.
            User user = userService.CreateUserData(userSignUpDTO);

            if(user == null) {
                body.put("code", 4002);
                return ResponseEntity.badRequest().body(body);
            }

            body.put("code", 2000);
            return ResponseEntity.ok().body(body);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<?> UserLogin(@RequestBody UserSIgnInDTO userSIgnInDTO, HttpServletResponse response) {
        try {
            body.clear();
            data.clear();
            // id와 password 를 기준으로 DB에 일치하는 유저 데이터를 불러온다.
            // 유저 데이터에 email을 토큰에 담아 accesstoken과 refreshToken을 생성한다.
            // accessToken은 클라이언트에서 관리할 수 있게 body에, refreshToken은 쿠키에 담겨 전달한다.
            User user = userService.FindUser(userSIgnInDTO);

            // 해당 이메일이 없다면 code 4003 응답
            if(userService.FindUserUseEmail(userSIgnInDTO.getEmail()) == null) {
                body.put("code", 4003);
                return ResponseEntity.badRequest().body(body);
            }
            // 해당 비밀번호가 다르다면 code 4001 응답
            else if(user == null) {
                body.put("code", 4001);
                return ResponseEntity.badRequest().body(body);
            }
            else {
                // 유저가 DB에 존재한다면 refreshToken을 발급하여 쿠키에 저장하여 보내준다.
                Cookie rt_cookie = new Cookie("refreshToken", userService.CreateToken(user, REFRESH_TIME));
                response.addCookie(rt_cookie);
                // key로 유저 email을 갖고 value로 refresh 값을 갖는 정보를 DB에 저장한다.
                RefreshToken refreshToken = userService.AddRefreshToken(user.getEmail(), rt_cookie.getValue());
                // refreshToken 값이 null이면 이미 해당 email에 refreshToken이 발급되어있다. 즉, 어디에서 로그인 되어있다는 것이다.
                if(refreshToken == null) {
                    body.put("code", 4012);
                    return ResponseEntity.badRequest().body(body);
                }

                // accessToken은 응답 바디로 넘겨준다.
                body.put("code", 2000);
                data.put("accessToken",  userService.CreateToken(user, ACCESS_TIME));
                body.put("data", data);
                return ResponseEntity.ok().body(body);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @PostMapping(value = "/signout")
    public ResponseEntity<?> UserSignOut(@RequestHeader Map<String, String> requestHeader, HttpServletRequest request, HttpServletResponse response) {
        // 토큰 유효성 검사 후 유저를 로그아웃 시킨다.
        // access token 이 유효하면 모든 토큰을 만료시킨다.
        Cookie[] cookies = request.getCookies();
        String cookiesResult = "";
        try {
            body.clear();
            // 헤더에 토큰이 없거나 refresh token이 없으면 응답코드 400을 응답한다.
            if(requestHeader.get("accesstoken") == null || cookies == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }

            // 쿠키에 존재하는 토큰을 가지고 유효성 검증을 한다.
            Map<String, String> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));
            // token에 email정보가 있다면 탈퇴 과정을 제대로 거친다.
            if(checkToken.get("email") != null) {
                // 유저 정보가 확인되면 token 키 값을 가진 쿠기가 제거돼야 한다.
                // DB에 있는 유저 email과 refreshToken 값이 제거돼야 한다.
                // 쿠키에 키 값이 "refreshToken"인 쿠키에 값을 찾아낸다.
                cookiesResult = userService.getStringCookie(cookies, cookiesResult, "refreshToken");

                User user = userService.FindUserUseEmail(checkToken.get("email"));
                RefreshToken rt = userService.DeleteRefreshToken(user.getEmail(), cookiesResult);

                // refresh token ID를 찾을 수 없을 때 응답을 해준다.
                if(rt == null) {
                    body.put("code", 4407);
                    return ResponseEntity.status(404).body(body);
                }
                // 모두 잘 처리됐다면 refresh token을 만료시킨다.
                userService.ExpirationToken(response, "refreshToken");
                body.put("code", 2000);
                return ResponseEntity.ok().body(body);
            }
            // 토큰에 email 정보가 없다면 그에 맞는 오류 응답을 낸다.
            else {
                body.put("code", checkToken.get("code"));
                return ResponseEntity.status(401).body(body);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> GetUserInfo(HttpServletRequest request) {
        // 토큰 유효성 검사 후 해당 유저의 데이터를 전달한다.
        // access token이 유효하면 DB에서 동일한 email값을 가진 유저 데이터를 찾아 응답한다.
        // 쿠키에 토큰이 없으면 응답코드 400을 응답한다.
        Cookie[] cookies = request.getCookies();
        String cookiesResult = "";
        try {
            body.clear();
            cookiesResult = userService.getStringCookie(cookies, cookiesResult, "accessToken");

            if(cookiesResult.equals("")) {
                body.put("message", "올바르지 않은 요청입니다.");
                return ResponseEntity.badRequest().body(body);
            }
            // 쿠키에 존재하는 토큰을 가지고 유효성 검증을 한다.
            Map<String, String> checkToken = userService.CheckToken(cookiesResult);

            if(checkToken.get("email") != null) {
                User user = userService.FindUserUseEmail(checkToken.get("email"));
                MakeUserInfoRes(user, body);

                return ResponseEntity.ok().body(body);
            }
            else {
                body.put("message", checkToken.get("message"));
                return ResponseEntity.status(401).body(body);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @PutMapping(value = "/user")
    public ResponseEntity<?> ModifyUserInfo(@RequestBody UserModifyDTO userModifyDTO, HttpServletRequest request) {
        // 토큰 유효성 검사 후 해당 유저의 데이터를 전달한다.
        // access token이 유효하면 DB에서 동일한 email값을 가진 유저 데이터를 찾아 데이터 수정 후 응답한다.
        // 쿠키에 토큰이 없으면 응답코드 400을 응답한다.
        Cookie[] cookies = request.getCookies();
        String cookiesResult = "";
        try {
            body.clear();
            cookiesResult = userService.getStringCookie(cookies, cookiesResult, "accessToken");

            if(cookiesResult.equals("")) {
                body.put("message", "올바르지 않은 요청입니다.");
                return ResponseEntity.badRequest().body(body);
            }

            // 쿠키에 존재하는 토큰을 가지고 유효성 검증을 한다.
            Map<String, String> checkToken = userService.CheckToken(cookiesResult);
            if(checkToken.get("email") != null) {
                User user = userService.ModifyUserData(userModifyDTO, checkToken.get("email"));
                MakeUserInfoRes(user, body);
                return ResponseEntity.ok().body(body);
            }
            else {
                body.put("message", checkToken.get("message"));
                return ResponseEntity.status(401).body(body);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @DeleteMapping(value = "/user")
    public ResponseEntity<?> DeleteUserinfo(HttpServletRequest request, HttpServletResponse response) {
        // 토큰 유효성 검사 후 해당 유저의 데이터를 전달한다.
        // access token이 유효하면 DB에서 동일한 email값을 가진 유저 데이터를 찾아 DB 데이터 삭제 후 응답한다.
        // 쿠키에 토큰이 없으면 응답코드 400을 응답한다.
        Cookie[] cookies = request.getCookies();
        String cookiesResult = "";
        try {
            body.clear();
            cookiesResult = userService.getStringCookie(cookies, cookiesResult, "accessToken");

            if(cookiesResult.equals("")) {
                body.put("message", "올바르지 않은 요청입니다.");
                return ResponseEntity.badRequest().body(body);
            }

            // 쿠키에 존재하는 토큰을 가지고 유효성 검증을 한다.
            Map<String, String> checkToken = userService.CheckToken(cookiesResult);
            if(checkToken.get("email") != null) {
                userService.DeleteUserData(checkToken.get("email"));
                // 유저 정보가 삭제되면 클라이언트에 token 키 값을 가진 쿠기가 제거돼야 한다.
                userService.ExpirationToken(response, "accessToken");
                userService.ExpirationToken(response, "refreshToken");
                return ResponseEntity.ok().body("");
            }
            else {
                body.put("message", checkToken.get("message"));
                return ResponseEntity.status(401).body(body);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    // 유저 정보를 바디로 보여주는 응답 형식에 맞춰 메시지를 만든다.
    private void MakeUserInfoRes(User user, HashMap<String, Object> map) {
        map.put("id", user.getId());
        map.put("admin_role", user.isAdminRole());
        map.put("email", user.getEmail());
        map.put("profile_picture", user.getProfilePicture());
        map.put("nickname",  user.getNickname());
        map.put("total_donation", user.getTotalDonation());
    }
}
