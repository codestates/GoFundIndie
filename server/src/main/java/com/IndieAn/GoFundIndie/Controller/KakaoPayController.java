package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Domain.DTO.KakaoPayApproveInputDTO;
import com.IndieAn.GoFundIndie.Service.KakaoPayService;
import com.IndieAn.GoFundIndie.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class KakaoPayController {
    private final KakaoPayService kakaoPayService;
    private final UserService userService;
    private HashMap<String, Object> body;

    @Autowired
    public KakaoPayController(KakaoPayService kakaoPayService, UserService userService) {
        this.kakaoPayService = kakaoPayService;
        this.userService = userService;
    }

    @GetMapping(value = "/pay/ready")
    public ResponseEntity<?> Payment(@RequestParam("amount") int amount, @RequestHeader Map<String, String> requestHeader) {
        body = new HashMap<>();
        try {
            // 헤더에 accesstoken이 없으면 4000 응답을 한다.
            if(requestHeader.get("accesstoken") == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }

            // 헤더에 존재하는 토큰을 가지고 유효성 검증을 한다.
            Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));

            // 토큰이 유효하다면 작성 기능을 수행한다.
            if(checkToken.get("email") != null) {
                body = kakaoPayService.KakaoPayReady(amount, (String)checkToken.get("email"));
                return ResponseEntity.status(body.get("data") == null ? 400 : 200).body(body);
            }
            else {
                return ResponseEntity.status(401).body(checkToken);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @PostMapping(value = "/pay/approve")
    public ResponseEntity<?> Approvement(@RequestBody KakaoPayApproveInputDTO kakaoPayApproveInputDTO, @RequestHeader Map<String, String> requestHeader) {
        body = new HashMap<>();
        try {
            // 헤더에 accesstoken이 없으면 4000 응답을 한다.
            if(requestHeader.get("accesstoken") == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }

            // 헤더에 존재하는 토큰을 가지고 유효성 검증을 한다.
            Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));

            // 토큰이 유효하다면 작성 기능을 수행한다.
            if(checkToken.get("email") != null) {
                body = kakaoPayService.kakaoPayInfo(kakaoPayApproveInputDTO, (String)checkToken.get("email"));
                return ResponseEntity.status(body.get("code").equals(2000) ? 200 : 400).body(body);
            }
            else {
                return ResponseEntity.status(401).body(checkToken);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }
}