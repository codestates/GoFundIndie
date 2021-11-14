package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Domain.DTO.*;
import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Service.BoardService;
import com.IndieAn.GoFundIndie.Service.CommentRatingService;
import com.IndieAn.GoFundIndie.Service.CommentService;
import com.IndieAn.GoFundIndie.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CommentController {
    private final CommentService commentService;
    private final CommentRatingService commentRatingService;
    private final UserService userService;
    private final BoardService boardService;
    private HashMap<String, Object> body = new HashMap<>();

    @Autowired
    public CommentController(CommentService commentService, UserService userService,
                             CommentRatingService commentRatingService, BoardService boardService) {
        this.commentService = commentService;
        this.userService = userService;
        this.commentRatingService = commentRatingService;
        this.boardService = boardService;
    }

    @PostMapping(value = "/comment")
    public ResponseEntity<?> WriteComment(@RequestHeader Map<String, String> requestHeader, @RequestBody CommentInputDTO commentInputDTO) {
        try {
            // 코멘트 서비스의 댓글을 만드는 기능을 실행한다.
            return commentService.WriteCommentData(requestHeader, commentInputDTO);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @GetMapping(value = "/comment/{boardId}")
    public ResponseEntity<?> GetComments(@PathVariable long boardId, @RequestParam(required = false) String type,
                                         @RequestParam(required = false) Integer page, @RequestHeader Map<String, String> requestHeader) {
        // 영화 보드에 작성된 댓글들을 불러오는 메소드
        // 해당 board가 존재하지 않으면 404 응답을 한다.
        try {
            body.clear();
            String email = null;
            // 헤더에 access token이 있다면 회원으로 보는 것이다.
            if(requestHeader.get("accesstoken") != null) {
                // 헤더에 존재하는 토큰을 가지고 유효성 검증을 한다.
                Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));
                // 검증이 되지 않는다면 응답 오류를 보낸다.
                if(checkToken.get("email") == null) {
                    return ResponseEntity.status(401).body(checkToken);
                }
                email = (String)checkToken.get("email");
            }

            // Board id로 board를 찾고, 없을 때의 응답을 추가한다.
            Board board = boardService.FindBoardId(boardId);
            if(board == null) {
                body.put("code", 4401);
                return ResponseEntity.status(404).body(body);
            }
            // token에 email정보가 유효하면 댓글을 가져오는 과정을 수행한다
            body = commentService.GetCommentPage(board, email, type, page);
            return ResponseEntity.status(body.get("data") == null ? 404 : 200).body(body);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @PutMapping(value = "/comment")
    public ResponseEntity<?> ModifyComment(@RequestBody CommentModifyDTO commentModifyDTO, @RequestHeader Map<String, String> requestHeader){
        // 작성된 댓글을 수정하는 기능
        try {
            return commentService.ModifyCommentData(commentModifyDTO, requestHeader);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }


    @DeleteMapping(value = "/comment")
    public ResponseEntity<?> DeleteComment(@RequestParam("comment_id") long commentId, @RequestHeader Map<String, String> requestHeader)  {
        // 작성된 댓글을 삭제하는 기능
        // 해당 id를 가진 comment가 존재하지 않으면 404 응답을 한다.
        try {
            body.clear();
            // 헤더에 accesstoken이 없으면 4000 응답을 한다.
            if(requestHeader.get("accesstoken") == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }

            // 헤더에 존재하는 토큰을 가지고 유효성 검증을 한다.
            Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));

            // token에 email정보가 있다면 댓글 삭제 과정을 수행한다
            if(checkToken.get("email") != null) {
                User user = userService.FindUserUseEmail((String) checkToken.get("email"));
                // 토큰으로 찾은 email이 DB에 존재하지 않으면 4000응답을 한다.
                if(user == null) {
                    body.put("code", 4000);
                    return ResponseEntity.badRequest().body(body);
                }

                body = commentService.DeleteComments(commentId, user);
                return ResponseEntity.status(body.get("code").equals(2000) ? 200 : 404).body(body);
            }
            else {
                return ResponseEntity.status(401).body(checkToken);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @PostMapping(value = "/rating")
    public ResponseEntity<?> RatingComment(@RequestBody RatingInputDTO commentId, @RequestHeader Map<String, String> requestHeader) {
        // 댓글 좋아요 기능
        try {
            body.clear();
            // 헤더에 accesstoken이 없으면 4000 응답을 한다.
            if(requestHeader.get("accesstoken") == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }

            // 헤더에 존재하는 토큰을 가지고 유효성 검증을 한다.
            Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));
            // token에 email정보가 있다면 좋아요 기능을 수행한다.
            if(checkToken.get("email") != null) {
                User user = userService.FindUserUseEmail((String) checkToken.get("email"));
                // 토큰으로 찾은 email이 DB에 존재하지 않으면 4000응답을 한다.
                if(user == null) {
                    body.put("code", 4000);
                    return ResponseEntity.badRequest().body(body);
                }
                body = commentRatingService.addRating(user, commentId.getCommentId());
                return ResponseEntity.status(body.get("code").equals(2000) ? 201 : 404).body(body);
            }
            else {
                return ResponseEntity.status(401).body(checkToken);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @PostMapping(value = "comment/report")
    public ResponseEntity<?> CreateReport(@RequestBody CommentReportInputDTO commentReportInputDTO, @RequestHeader Map<String, String> requestHeader) {
        // 댓글을 신고하는 기능
        try {
            return commentService.AddReport(commentReportInputDTO, requestHeader);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @DeleteMapping(value = "comment/report")
    public ResponseEntity<?> DeleteReport(@RequestBody CommentReportDeleteDTO commentReportDeleteDTO, @RequestHeader Map<String, String> requestHeader) {
        // 댓글 신고 목록을 삭제하는 기능
        try {
            return commentService.RemoveReport(commentReportDeleteDTO, requestHeader);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }
}
