package com.IndieAn.GoFundIndie.Service;

import com.IndieAn.GoFundIndie.Domain.DTO.KakaoPayApproveInputDTO;
import com.IndieAn.GoFundIndie.Domain.DTO.KakaoPayApproveVO;
import com.IndieAn.GoFundIndie.Domain.DTO.KakaoPayReadyVO;
import com.IndieAn.GoFundIndie.Domain.Entity.PayRequest;
import com.IndieAn.GoFundIndie.Repository.PayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final PayRepository payRepository;
    private HashMap<String, Object> body;

    @Value("#{info['gofund.kko.adminkey']}")
    private String KKO_ADMIN_KEY;

    @Autowired
    public KakaoPayService(PayRepository payRepository) {
        this.payRepository = payRepository;
    }

    // 카카오 페이 결제 요청 서비스
    public HashMap<String, Object> KakaoPayReady(Integer amount, String email) {
        body = new HashMap<>();
        // 해당 이메일을 가진 결제 요청이 존재한다면 오류 응답을 낸다.
        if(payRepository.FindPayRequestByEmail(email) != null) {
            body.put("code", 4002);
            body.put("data", null);
            return body;
        }

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
        params.add("total_amount", Integer.toString(amount));
        params.add("tax_free_amount", "0");
        params.add("approval_url", "https://localhost:3000");
        params.add("cancel_url", "https://localhost:3000");
        params.add("fail_url", "https://localhost:3000");

        HttpEntity<MultiValueMap<String, String>> postBody = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), postBody, KakaoPayReadyVO.class);

            log.info("" + kakaoPayReadyVO);

            if(kakaoPayReadyVO != null) {
                // 성공적으로 요청이 완료되면 결제 요청 정보를 저장한다.
                payRepository.CreatePayRequest(email, kakaoPayReadyVO.getTid(), amount);
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
    public HashMap<String, Object> kakaoPayInfo(KakaoPayApproveInputDTO kakaoPayApproveInputDTO, String email) {
        body = new HashMap<>();
        // 해당 이메일을 가진 결제 요청이 존재하지 않는다면 오류 응답을 낸다.
        PayRequest payRequest = payRepository.FindPayRequestByEmail(email);
        if(payRequest == null) {
            body.put("code", 4003);
            body.put("data", null);
            return body;
        }

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
        params.add("tid", payRequest.getTid());
        params.add("partner_order_id", "partner_order_id");
        params.add("partner_user_id", "partner_user_id");
        params.add("pg_token", kakaoPayApproveInputDTO.getPg_token());
        params.add("total_amount", Integer.toString(payRequest.getAmount()));

        HttpEntity<MultiValueMap<String, String>> postBody = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            // 승인 요청을 보내기 전에 결제 요청 정보를 삭제한다.
            payRepository.DeletePayRequest(payRequest.getId());
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