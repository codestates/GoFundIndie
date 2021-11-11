package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Domain.DTO.KakaoPayApproveInputDTO;
import com.IndieAn.GoFundIndie.Service.KakaoPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class KakaoPayController {
    private final KakaoPayService kakaoPayService;

    @Autowired
    public KakaoPayController(KakaoPayService kakaoPayService) {
        this.kakaoPayService = kakaoPayService;
    }

    @GetMapping(value = "/pay/ready")
    public ResponseEntity<?> Payment(@RequestParam("amount") int amount, @RequestHeader Map<String, String> requestHeader) {
        try {
            return kakaoPayService.KakaoPayReadyRequest(amount, requestHeader);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @PostMapping(value = "/pay/approve")
    public ResponseEntity<?> Approvement(@RequestBody KakaoPayApproveInputDTO kakaoPayApproveInputDTO, @RequestHeader Map<String, String> requestHeader) {
        try {
            return kakaoPayService.KakaoPayApproval(kakaoPayApproveInputDTO, requestHeader);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }
}