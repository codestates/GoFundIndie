package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Domain.DTO.CommentInputDTO;
import com.IndieAn.GoFundIndie.Domain.DTO.CommentOutputDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.Comment;
import com.IndieAn.GoFundIndie.Domain.Entity.CommentRating;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.JPAInterface.CommentJPAInterface;
import com.IndieAn.GoFundIndie.Service.CommentService;
import com.IndieAn.GoFundIndie.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final HashMap<String, Object> body = new HashMap<>();
    @Autowired
    CommentJPAInterface commentJPAInterface;

    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping(value = "/comment")
    public ResponseEntity<?> WriteComment(@RequestHeader Map<String, String> requestHeader, @RequestBody CommentInputDTO commentInputDTO) {
        try {
            body.clear();

            // 헤더에 accesstoken이 없으면 4000 응답을 한다.
            if(requestHeader.get("accesstoken") == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }

            // 헤더에 존재하는 토큰을 가지고 유효성 검증을 한다.
            Map<String, String> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));

            // 토큰이 유효하다면 작성 기능을 수행한다.
            if(checkToken.get("email") != null) {
                User user = userService.FindUserUseEmail(checkToken.get("email"));
                // 입력으로 들어온 userId가 DB에 존재하지 않으면 4400 응답을 한다.
                if(userService.FindUserById(commentInputDTO.getUserId()) == null) {
                    body.put("code", 4400);
                    return ResponseEntity.status(404).body(body);
                }
                // Board Controller 가 작성이 됐을 때 id로 board를 찾고, 없을 때의 응답을 추가한다.
//                if(board를 찾는 메소드 == null) {
//                    body.put("code", 4401);
//                    return ResponseEntity.status(404).body(body);
//                }

                // 토큰으로 얻은 email의 pk와 입력으로 들어온 user의 pk가 다르다면 400 응답을 한다.
                if(user.getId() != commentInputDTO.getUserId()) {
                    body.put("code", 4014);
                    return ResponseEntity.badRequest().body(body);
                }

                // comment를 작성하는 기능을 한다.
                Comment comment = commentService.AddCommentData(commentInputDTO);
                // 이미 comment 해당 board에 작성했다면 작성할 수 없다.
                if(comment == null) {
                    body.put("code", 4004);
                    return ResponseEntity.badRequest().body(body);
                }

                body.put("code", 2000);
                return ResponseEntity.ok().body(body);
            }
            else {
                body.put("code", checkToken.get("code"));
                return ResponseEntity.status(401).body(body);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("err");
        }
    }

    @GetMapping(value = "/comment/{boardId}")
    public ResponseEntity<?> GetComments(@PathVariable Long boardId, @PageableDefault(size = 10) Pageable pageable) {
        body.clear();
        Board board = commentService.FindBoard(boardId);
        Page<Comment> comments = commentJPAInterface.findByBoardId(board, pageable);
        Page<CommentOutputDTO> commentList = comments.map(
                comment -> {
                    int like = 0;
                    int dislike = 0;

                    for(CommentRating rating : comment.getCommentRatings()) {
                        if(rating.isLike()) like++;
                        else if(rating.isDislike()) dislike++;
                    }

                    return new CommentOutputDTO(
                            comment.getId(),
                            comment.getRating(),
                            comment.getUserId().getId(),
                            comment.getUserId().getNickname(),
                            comment.getUserId().getProfilePicture(),
                            comment.getDonation(),
                            comment.getBody(),
                            comment.isSpoiler(),
                            like,
                            dislike
                    );
                }
        );
        body.put("data", commentList);

        return ResponseEntity.ok().body(body);
    }
}
