package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Domain.DTO.CommentInputDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.Comment;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Service.CommentService;
import com.IndieAn.GoFundIndie.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final HashMap<String, Object> body = new HashMap<>();

    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping(value = "/comment")
    public ResponseEntity<?> WriteComment(HttpServletRequest request, @RequestBody CommentInputDTO commentInputDTO) {
        Cookie[] cookies = request.getCookies();
        String cookiesResult = "";
        try {
            body.clear();
            // 쿠키에 키 값이 "accessToken"인 쿠키에 값을 찾아낸다.
            cookiesResult = userService.getStringCookie(cookies, cookiesResult, "accessToken");

            if(cookiesResult.equals("")) {
                body.put("message", "token required");
                return ResponseEntity.badRequest().body(body);
            }
            // 쿠키에 존재하는 토큰을 가지고 유효성 검증을 한다.
            Map<String, String> checkToken = userService.CheckToken(cookiesResult);
            if(checkToken.get("email") != null) {
                User user = userService.FindUserUseEmail(checkToken.get("email"));
                // 토큰으로 얻은 email의 pk와 입력으로 들어온 user의 pk가 다르다면 400 응답을 한다.
                if(user.getId() != commentInputDTO.getUserId()) {
                    body.put("message", "Unauthorized!! An access token is required");
                    return ResponseEntity.status(401).body(body);
                }

                // comment를 작성하는 기능을 한다.
                Comment comment = commentService.AddCommentData(commentInputDTO);
                // 이미 comment 해당 board에 작성했다면 작성할 수 없다.
                if(comment == null) {
                    body.put("message", "You have already commented on that board");
                    return ResponseEntity.badRequest().body(body);
                }

                return ResponseEntity.ok().body("Comment successful");
            }
            else {
                body.put("message", checkToken.get("message"));
                return ResponseEntity.status(401).body(body);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @GetMapping(value = "/comment")
    public ResponseEntity<?> GetCommentList(@RequestParam(name = "board_id") long boardId) {
        try {
            // *** boardId로 해당 보드가 DB에 존재하는지 확인하는 메소드 필요. Board가 완료되면 작성할것 ***
            List<Comment> commentList = commentService.getCommentsData(boardId);
            body.put("comments", commentList);
            return ResponseEntity.ok().body(body);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }
}
