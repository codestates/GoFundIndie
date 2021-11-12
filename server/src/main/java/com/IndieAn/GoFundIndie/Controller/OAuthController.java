package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Service.KakaoLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class OAuthController {
    private final KakaoLoginService kakaoLoginService;

    @Autowired
    public OAuthController(KakaoLoginService kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }

    @GetMapping(value = "/oauth_kakao")
    public ResponseEntity<?> KakaoLogin(@RequestParam("code") String code) {
        try {
            return kakaoLoginService.getAccessToken(code);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @GetMapping(value = "/logout_kakao")
    public ResponseEntity<?> KakaoLogout(@RequestHeader Map<String, String> header) {
        try {
            return kakaoLoginService.Logout(header.get("accesstoken"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }
}
