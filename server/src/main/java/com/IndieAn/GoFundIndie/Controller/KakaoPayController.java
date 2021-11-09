package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Domain.DTO.KakaoPayApproveInputDTO;
import com.IndieAn.GoFundIndie.Service.KakaoPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RestController
public class KakaoPayController {
    private final KakaoPayService kakaoPayService;
    private HashMap<String, Object> body;

    @Autowired
    public KakaoPayController(KakaoPayService kakaoPayService) {
        this.kakaoPayService = kakaoPayService;
    }

    @GetMapping(value = "/pay/ready")
    public ResponseEntity<?> Payment(@RequestParam("amount") Integer amount) {
        body = new HashMap<>();
        try {
            body = kakaoPayService.KakaoPayReady(amount);
            return ResponseEntity.status(body.get("data") == null ? 400 : 200).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @PostMapping(value = "/pay/approve")
    public ResponseEntity<?> Approvement(@RequestBody KakaoPayApproveInputDTO kakaoPayApproveInputDTO) {
        body = new HashMap<>();
        try {
            body = kakaoPayService.kakaoPayInfo(kakaoPayApproveInputDTO);
            return ResponseEntity.status(body.get("code").equals(2000) ? 200 : 400).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }
}