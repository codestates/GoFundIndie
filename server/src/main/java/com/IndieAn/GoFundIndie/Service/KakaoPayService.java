package com.IndieAn.GoFundIndie.Service;

import com.IndieAn.GoFundIndie.Domain.DTO.KakaoPayApproveInputDTO;
import com.IndieAn.GoFundIndie.Domain.DTO.KakaoPayApproveVO;
import com.IndieAn.GoFundIndie.Domain.DTO.KakaoPayReadyVO;
import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.Comment;
import com.IndieAn.GoFundIndie.Domain.Entity.PayRequest;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.CommentRepository;
import com.IndieAn.GoFundIndie.Repository.PayRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class KakaoPayService {
    private static final String HOST = "https://kapi.kakao.com";
    private final PayRepository payRepository;
    private final UserService userService;
    private final BoardService boardService;
    private final CommentRepository commentRepository;
    private HashMap<String, Object> body;

    @Value("#{info['gofund.kko.adminkey']}")
    private String KKO_ADMIN_KEY;

    @Autowired
    public KakaoPayService(PayRepository payRepository, UserService userService,
                           BoardService boardService, CommentRepository commentRepository) {
        this.payRepository = payRepository;
        this.userService = userService;
        this.boardService = boardService;
        this.commentRepository = commentRepository;
    }

    // 결제 요청 결과를 컨트롤러에 반환해주는 서비스
    public ResponseEntity<?> KakaoPayReadyRequest(int amount, long boardId, Map<String, String> requestHeader) {
        body = new HashMap<>();
        // 해당 보드가 존재하지 않으면 4401 응답을 낸다.
        Board board = boardService.FindBoardId(boardId);
        if(board == null) {
            body.put("code", 4401);
            return ResponseEntity.status(404).body(body);
        }
        // 헤더에 accesstoken이 없으면 4000 응답을 한다.
        if(requestHeader.get("accesstoken") == null) {
            body.put("code", 4000);
            return ResponseEntity.badRequest().body(body);
        }

        // 헤더에 존재하는 토큰을 가지고 유효성 검증을 한다.
        Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));

        // 토큰이 유효하다면 결제 요청 기능을 수행한다.
        if(checkToken.get("email") != null) {
            // 해당 보드에 자신이 작성한 댓글이 있는지 확인한다. 없다면 4016 응답
            User user = userService.FindUserUseEmail((String)checkToken.get("email"));
            boolean isFind = false;
            for(Comment c : board.getComments()) {
                if(c.getUserId().getId() == user.getId()) {
                    isFind = true;
                    break;
                }
            }
            if(!isFind) {
                body.put("code", 4016);
                return ResponseEntity.badRequest().body(body);
            }

            body = KakaoPayReady(amount, (String)checkToken.get("email"));
            return ResponseEntity.status(body.get("data") == null ? 400 : 200).body(body);
        }
        else {
            return ResponseEntity.status(401).body(checkToken);
        }
    }

    // 결제 요청 결과를 컨트롤러에 반환해주는 서비스
    public ResponseEntity<?> KakaoPayApproval(KakaoPayApproveInputDTO kakaoPayApproveInputDTO, Map<String, String> requestHeader) {
        body = new HashMap<>();
        // 해당 보드가 존재하지 않으면 4401 응답을 낸다.
        Board board = boardService.FindBoardId(kakaoPayApproveInputDTO.getBoardId());
        if(board == null) {
            body.put("code", 4401);
            return ResponseEntity.status(404).body(body);
        }

        // 헤더에 accesstoken이 없으면 4000 응답을 한다.
        if(requestHeader.get("accesstoken") == null) {
            body.put("code", 4000);
            return ResponseEntity.badRequest().body(body);
        }

        // 헤더에 존재하는 토큰을 가지고 유효성 검증을 한다.
        Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));

        // 토큰이 유효하다면 결제 승인 기능을 수행한다.
        if(checkToken.get("email") != null) {
            // 요청에서 검증을 했으므로 도네이션 완료 후 수정할 코멘트 아이디를 찾는다.
            User user = userService.FindUserUseEmail((String)checkToken.get("email"));
            long commentId = -1;
            for(Comment c : board.getComments()) {
                if(c.getUserId().getId() == user.getId()) {
                    commentId = c.getId();
                    break;
                }
            }

            body = kakaoPayInfo(kakaoPayApproveInputDTO, (String)checkToken.get("email"), commentId);
            return ResponseEntity.status(body.get("code").equals(2000) ? 200 : 400).body(body);
        }
        else {
            return ResponseEntity.status(401).body(checkToken);
        }
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
        params.add("approval_url", "https://localhost:3000/approval");
        params.add("cancel_url", "https://localhost:3000/approval");
        params.add("fail_url", "https://localhost:3000/approval");

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
                // 이것이 실행될 때면 아래 코드는 실행되지 않고 서버오류가 난다.
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
    public HashMap<String, Object> kakaoPayInfo(KakaoPayApproveInputDTO kakaoPayApproveInputDTO, String email, long commentId) {
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
            // 정상적으로 완료가 됐다면 해당 코멘트의 도네이션 정보를 바꿔준다.
            if (kakaoPayApproveVO != null) {
                commentRepository.ModifyDonation(commentId, kakaoPayApproveVO.getAmount().getTotal());
            }

        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return body;
    }
}