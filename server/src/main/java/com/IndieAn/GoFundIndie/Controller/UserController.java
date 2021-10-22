package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Domain.DTO.UserSignUpDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "signup")
    public ResponseEntity<?> UserLogin(@RequestBody UserSignUpDTO  userSignUpDTO) {
        try {
            // DB에 해당 email이 존재하는지 확인한다. 반환 값이 null이라면 이미 존재하는 email이다.
            User user = userService.CreateUserData(userSignUpDTO);

            if(user == null) {
                return ResponseEntity.badRequest().body(new HashMap<>() {{
                    put("message", "올바른 요청이 아닙니다");
                }});
            }

            return ResponseEntity.ok().body("");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }
}
