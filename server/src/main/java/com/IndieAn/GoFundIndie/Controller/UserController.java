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
public class UserController {
    private final UserService userService;
    // accessToken 유효 시간
    private final static Integer ACCESS_TIME = 30;
    // refreshToken 유효 시간
    private final static Integer REFRESH_TIME = 30 * 24;
    private HashMap<String, Object> body = new HashMap<>();
    private HashMap<String, Object> data = new HashMap<>();

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/check")
    public ResponseEntity<?> CheckDuplicate(@RequestParam(name = "type") String type, @RequestParam(name = "query") String query) {
        // email이나 nickname의 중복체크를 위한 api
        try {
            body.clear();
            // type이 email일 경우
            if(type.equals("email")) {
                User user = userService.FindUserUseEmail(query);
                // user가 null이 아니라면 4002 응답을 한다.
                if(user != null) {
                    body.put("code", 4002);
                    return ResponseEntity.badRequest().body(body);
                }
            }
            // type이 닉네임일 경우
            else {
                User user = userService.CheckUserByNickname(query);
                // user가 null이 아니라면 4013 응답을 한다.
                if(user != null) {
                    body.put("code", 4013);
                    return ResponseEntity.badRequest().body(body);
                }
            }
            // null이라면 없는 것이므로 2000 응답을 한다.
            body.put("code", 2000);
            return ResponseEntity.ok().body(body);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
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
            return ResponseEntity.status(201).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<?> UserLogin(@RequestBody UserSIgnInDTO userSIgnInDTO) {
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
                // accessToken은 응답 바디로 넘겨준다.
                body.put("code", 2000);
                data.put("accessToken", userService.CreateToken(user, ACCESS_TIME));
                data.put("refreshToken", userService.CreateToken(user, REFRESH_TIME));

                // key로 유저 email을 갖고 value로 refresh 값을 갖는 정보를 DB에 저장한다.
                RefreshToken refreshToken = userService.AddRefreshToken(user.getEmail(), (String)data.get("refreshToken"));
                // refreshToken 값이 null이면 이미 해당 email에 refreshToken이 발급되어있다. 즉, 어디에서 로그인 되어있다는 것이다.
                if(refreshToken == null) {
                    body.put("code", 4012);
                    return ResponseEntity.badRequest().body(body);
                }

                body.put("data", data);
                return ResponseEntity.ok().body(body);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @PostMapping(value = "/signout")
    public ResponseEntity<?> UserSignOut(@RequestHeader Map<String, String> requestHeader) {
        // 토큰 유효성 검사 후 유저를 로그아웃 시킨다.
        // access token 이 유효하면 모든 토큰을 만료시킨다.
        try {
            body.clear();
            // 헤더에 access token이 없거나 refresh token이 없으면 응답코드 400을 응답한다.
            if(requestHeader.get("accesstoken") == null || requestHeader.get("refreshtoken") == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }

            // 헤더에 존재하는 토큰을 가지고 유효성 검증을 한다.
            Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));
            // token에 email정보가 있다면 로그아웃 과정을 수행한다.
            if(checkToken.get("email") != null) {
                // 유저 정보가 확인되면 token 키 값을 가진 쿠키가 제거돼야 한다.
                // DB에 있는 유저 email과 refreshToken 값이 제거돼야 한다.

                User user = userService.FindUserUseEmail((String)checkToken.get("email"));
                // 토큰으로 찾은 email이 DB에 존재하지 않으면 4000응답을 한다.
                if(user == null) {
                    body.put("code", 4000);
                    return ResponseEntity.badRequest().body(body);
                }
                RefreshToken rt = userService.DeleteRefreshToken(user.getEmail(), requestHeader.get("refreshtoken"));

                // refresh token ID를 찾을 수 없을 때 응답을 해준다.
                if(rt == null) {
                    body.put("code", 4407);
                    return ResponseEntity.status(404).body(body);
                }

                body.put("code", 2000);
                return ResponseEntity.ok().body(body);
            }
            // 토큰에 email 정보가 없다면 그에 맞는 오류 응답을 낸다.
            else {
                return ResponseEntity.status(401).body(checkToken);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> GetUserInfo(@RequestHeader Map<String, String> requestHeader) {
        // 토큰 유효성 검사 후 해당 유저의 데이터를 전달한다.
        // access token이 유효하면 DB에서 동일한 email값을 가진 유저 데이터를 찾아 응답한다.
        // 헤더에 토큰이 없으면 응답코드 400을 응답한다.
        try {
            body.clear();
            data.clear();

            if(requestHeader.get("accesstoken") == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }
            // 헤더에 존재하는 토큰을 가지고 유효성 검증을 한다.
            Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));

            // token에 email정보가 있다면 정보를 가져오는 과정을 수행한다.
            if(checkToken.get("email") != null) {
                User user = userService.FindUserUseEmail((String)checkToken.get("email"));
                // 토큰으로 찾은 email이 DB에 존재하지 않으면 4000응답을 한다.
                if(user == null) {
                    body.put("code", 4000);
                    return ResponseEntity.badRequest().body(body);
                }
                userService.MakeUserInfoRes(user, data);
                body.put("code", 2000);
                body.put("data", data);
                return ResponseEntity.ok().body(body);
            }
            else {
                return ResponseEntity.status(401).body(checkToken);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @PutMapping(value = "/user")
    public ResponseEntity<?> ModifyUserInfo(@RequestBody UserModifyDTO userModifyDTO, @RequestHeader Map<String, String> requestHeader) {
        // 토큰 유효성 검사 후 해당 유저의 데이터를 전달한다.
        // access token이 유효하면 DB에서 동일한 email값을 가진 유저 데이터를 찾아 데이터 수정 후 응답한다.
        // 헤더에 토큰이 없으면 응답코드 400을 응답한다.
        try {
            body.clear();
            data.clear();

            if(requestHeader.get("accesstoken") == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }

            // 헤더에 존재하는 토큰을 가지고 유효성 검증을 한다.
            Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));

            // token에 email정보가 있다면 정보를 수정하는 과정을 수행한다.
            if(checkToken.get("email") != null) {
                // 요청 바디에 어떤 값도 들어오지 않으며, 광고수신 동의 값이 똑같을 때 4006 오류를 낸다.
                if(userModifyDTO.getNickname() == null && userModifyDTO.getPassword() == null
                        && userModifyDTO.getProfilePic() == null && userModifyDTO.isAdAgree() == userService.FindUserUseEmail((String)checkToken.get("email")).isAdAgree()) {
                    body.put("code", 4006);
                    return ResponseEntity.badRequest().body(body);
                }

                User user = userService.ModifyUserData(userModifyDTO, (String)checkToken.get("email"));
                // 토큰으로 찾은 email이 DB에 존재하지 않으면 4000응답을 한다.
                if(user == null) {
                    body.put("code", 4000);
                    return ResponseEntity.badRequest().body(body);
                }
                userService.MakeUserInfoRes(user, data);
                body.put("code", 2000);
                body.put("data", data);
                return ResponseEntity.ok().body(body);
            }
            else {
                return ResponseEntity.status(401).body(checkToken);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @DeleteMapping(value = "/user")
    public ResponseEntity<?> DeleteUserinfo(@RequestHeader Map<String, String> requestHeader) {
        // 토큰 유효성 검사 후 해당 유저의 데이터를 전달한다.
        // access token이 유효하면 DB에서 동일한 email값을 가진 유저 데이터를 찾아 DB 데이터 삭제 후 응답한다.
        try {
            body.clear();
            // 헤더에 access token이 없거나 refresh token이 없으면 응답코드 400을 응답한다.
            if(requestHeader.get("accesstoken") == null || requestHeader.get("refreshtoken") == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }

            // 헤더에 존재하는 토큰을 가지고 유효성 검증을 한다.
            Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));

            if(checkToken.get("email") != null) {
                // 유저 정보가 확인되면 token 키 값을 가진 쿠키가 제거돼야 한다.
                // DB에 있는 유저 email과 refreshToken 값이 제거돼야 한다.

                User user = userService.FindUserUseEmail((String)checkToken.get("email"));
                // 토큰으로 찾은 email이 DB에 존재하지 않으면 4000응답을 한다.
                if(user == null) {
                    body.put("code", 4000);
                    return ResponseEntity.badRequest().body(body);
                }
                RefreshToken rt = userService.DeleteRefreshToken(user.getEmail(), requestHeader.get("refreshtoken"));

                // refresh token ID를 찾을 수 없을 때 응답을 해준다.
                if(rt == null) {
                    body.put("code", 4407);
                    return ResponseEntity.status(404).body(body);
                }
                // DB에 유저 email과 refresh token 쌍이 제거됐다면, 해당 email을 가진 유저를 DB에서 삭제한다.
                userService.DeleteUserData((String)checkToken.get("email"));

                body.put("code", 2000);
                return ResponseEntity.ok().body(body);
            }
            else {
                return ResponseEntity.status(401).body(checkToken);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @GetMapping(value = "/reissuance")
    public ResponseEntity<?> ReissueAccessToken(@RequestHeader Map<String, String> requestHeader) {
        // access token이 만료됐을 때, refresh token을 검증해 새로운 access token을 발급받는다.
        try {
            body.clear();
            data.clear();

            // 쿠키에 refresh token이 없으면 응답코드 400을 응답한다.
            if(requestHeader.get("refreshtoken") == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }

            // 헤더에 존재하는 refresh token을 가지고 유효성 검증을 한다.
            Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("refreshtoken"));

            if(checkToken.get("email") != null) {
                // 해당 refresh token이 가지고 있는 email로 다시 access token을 발급한다.
                User user = userService.FindUserUseEmail((String)checkToken.get("email"));
                // 토큰으로 찾은 email이 DB에 존재하지 않으면 4000응답을 한다.
                if(user == null) {
                    body.put("code", 4000);
                    return ResponseEntity.badRequest().body(body);
                }

                RefreshToken rt = userService.FindRefreshToken(user.getEmail(), requestHeader.get("refreshtoken"));

                // refresh token를 찾을 수 없을 때 응답을 해준다.
                if(rt == null) {
                    body.put("code", 4407);
                    return ResponseEntity.status(404).body(body);
                }
                // accessToken은 응답 바디로 넘겨준다.
                body.put("code", 2000);
                data.put("accessToken",  userService.CreateToken(user, ACCESS_TIME));
                body.put("data", data);
                return ResponseEntity.ok().body(body);
            }
            // 토큰에 email 정보가 없다면 그에 맞는 오류 응답을 낸다.
            else {
                return ResponseEntity.status(401).body(checkToken);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }
}
