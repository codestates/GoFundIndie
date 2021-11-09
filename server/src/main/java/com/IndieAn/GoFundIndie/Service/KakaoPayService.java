package com.IndieAn.GoFundIndie.Service;

import com.IndieAn.GoFundIndie.Domain.DTO.KakaoPayApproveInputDTO;
import com.IndieAn.GoFundIndie.Domain.DTO.KakaoPayApproveVO;
import com.IndieAn.GoFundIndie.Domain.DTO.KakaoPayReadyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

@Slf4j
@Service
public class KakaoPayService {
    private static final String HOST = "https://kapi.kakao.com";
    private HashMap<String, Object> body;
    private String tid;
    private Integer amount;

    @Value("#{info['gofund.kko.adminkey']}")
    private String KKO_ADMIN_KEY;

    // 카카오 페이 결제 요청 서비스
    public HashMap<String, Object> KakaoPayReady(Integer amount) {
        body = new HashMap<>();
        this.amount = amount;
        KakaoPayReadyVO kakaoPayReadyVO;
        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + KKO_ADMIN_KEY);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "partner_order_id");
        params.add("partner_user_id", "partner_user_id");
        params.add("item_name", "Movie Donation");
        params.add("quantity", "1");
        params.add("total_amount", Integer.toString(this.amount));
        params.add("tax_free_amount", "0");
        params.add("approval_url", "https://localhost:3000/approval");
        params.add("cancel_url", "https://localhost:3000/approval");
        params.add("fail_url", "https://localhost:3000/approval");

        HttpEntity<MultiValueMap<String, String>> postBody = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), postBody, KakaoPayReadyVO.class);

            log.info("" + kakaoPayReadyVO);

            if(kakaoPayReadyVO != null) {
                tid = kakaoPayReadyVO.getTid();
                body.put("code", 2000);
                body.put("data", kakaoPayReadyVO);
            }
            else {
                body.put("code", 4016);
                body.put("data", null);
            }
            return body;

        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    // 카카오페이 결제 승인 서비스
    public HashMap<String, Object> kakaoPayInfo(KakaoPayApproveInputDTO kakaoPayApproveInputDTO) {
        body = new HashMap<>();
        KakaoPayApproveVO kakaoPayApproveVO;
        log.info("KakaoPayInfoVO............................................");

        RestTemplate restTemplate = new RestTemplate();

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + KKO_ADMIN_KEY);
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", tid);
        params.add("partner_order_id", "partner_order_id");
        params.add("partner_user_id", "partner_user_id");
        params.add("pg_token", kakaoPayApproveInputDTO.getPg_token());
        params.add("total_amount", Integer.toString(this.amount));

        HttpEntity<MultiValueMap<String, String>> postBody = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayApproveVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), postBody, KakaoPayApproveVO.class);
            log.info("" + kakaoPayApproveVO);

            body.put("code", 2000);

        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return body;
    }
}
