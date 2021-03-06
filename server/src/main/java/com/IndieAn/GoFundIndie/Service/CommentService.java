package com.IndieAn.GoFundIndie.Service;

import com.IndieAn.GoFundIndie.Domain.DTO.*;
import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.Comment;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.CommentRatingRepository;
import com.IndieAn.GoFundIndie.Repository.CommentReportRepository;
import com.IndieAn.GoFundIndie.Repository.CommentRepository;
import com.IndieAn.GoFundIndie.Repository.JPAInterface.CommentJPAInterface;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentJPAInterface commentJPAInterface;
    private final UserRepository userRepository;
    private final CommentRatingRepository commentRatingRepository;
    private final CommentReportRepository commentReportRepository;
    private final UserService userService;
    private final BoardService boardService;
    private HashMap<String, Object> body;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentJPAInterface commentJPAInterface,
                          UserRepository userRepository, CommentRatingRepository commentRatingRepository,
                          CommentReportRepository commentReportRepository, UserService userService,
                          BoardService boardService) {
        this.commentRepository = commentRepository;
        this.commentJPAInterface = commentJPAInterface;
        this.userRepository = userRepository;
        this.commentRatingRepository = commentRatingRepository;
        this.commentReportRepository = commentReportRepository;
        this.userService = userService;
        this.boardService = boardService;
    }

    // Comment??? ???????????? ????????? ?????? ????????? ??????
    public ResponseEntity<?> WriteCommentData(Map<String, String> requestHeader, CommentInputDTO commentInputDTO) {
        body = new HashMap<>();
        // ????????? accesstoken??? ????????? 4000 ????????? ??????.
        if(requestHeader.get("accesstoken") == null) {
            body.put("code", 4000);
            return ResponseEntity.badRequest().body(body);
        }

        // ????????? ???????????? ????????? ????????? ????????? ????????? ??????.
        Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));

        // ????????? ??????????????? ?????? ????????? ????????????.
        if(checkToken.get("email") != null) {
            User user = userService.FindUserUseEmail((String)checkToken.get("email"));
            // ???????????? ?????? email??? DB??? ???????????? ????????? 4000????????? ??????.
            if(user == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }
            // Board id??? board??? ??????, ?????? ?????? ????????? ????????????.
            if(boardService.FindBoardId(commentInputDTO.getBoardId()) == null) {
                body.put("code", 4401);
                return ResponseEntity.status(404).body(body);
            }

            // comment??? ?????? ??? board id ??? ??? user id??? ????????? ????????????. ????????? ?????? comment??? ????????? ????????? ????????? 4004????????? ??????.
            for(Comment c : commentRepository.FindCommentList()) {
                if(c.getBoardId().getId() == commentInputDTO.getBoardId() && c.getUserId().getId() == user.getId()) {
                    body.put("code", 4004);
                    return ResponseEntity.badRequest().body(body);
                }
            }
            // comment ????????? ??????.
            commentRepository.AddComment(commentInputDTO, user);
            body.put("code", 2000);
            return ResponseEntity.status(201).body(body);
        }
        else {
            return ResponseEntity.status(401).body(checkToken);
        }
    }

    // ??? ????????? ?????? Comment?????? ???????????? ????????? ??????
    public HashMap<String, Object> GetCommentPage(Board board, String email, String type, Integer page) {
        body = new HashMap<>();

        // email??? ????????? ????????? ??????
        User user = null;
        if(email != null) {
            user = userRepository.FindUserByEmail(email);
            // ???????????? ?????? email??? DB??? ???????????? ????????? 4000????????? ??????.
            if(user == null) {
                body.put("code", 4000);
                return body;
            }
        }
        // ???????????? ?????? ????????? ?????? ???????????? ????????????.
        if(page == null) page = 1;
        if(type == null) type = "new";
        // ??????????????? order??? like??? ??????, ??? ????????? id??? ??????????????? ??????????????? ??????.
        String order = type.equals("pop") ? "like" : "id";
        PageRequest pageable = PageRequest.of(page-1, 10, Sort.by(order).descending());
        Page<Comment> comments = commentJPAInterface.findByBoardId(board, pageable);
        User findUser = user;
        Page<CommentOutputDTO> commentList = comments.map(
                comment -> {
                    boolean ratingChecked = false;
                    if(findUser != null) {
                        if(commentRatingRepository.FindRatingByUserAndComment(findUser.getId(), comment.getId()) != null) {
                            ratingChecked = true;
                        }
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
                            comment.getLike(),
                            ratingChecked
                    );
                });

        body.put("code", 2000);
        body.put("data", commentList);
        return body;
    }

    // Comment??? ???????????? ????????? ?????? ????????? ??????
    public ResponseEntity<?> ModifyCommentData(CommentModifyDTO commentModifyDTO, Map<String, String> requestHeader) {
        body = new HashMap<>();
        // ?????? ????????? ???????????? ????????? 4401 ????????? ??????.
        Board board = boardService.FindBoardId(commentModifyDTO.getBoardId());
        if(board == null) {
            body.put("code", 4401);
            return ResponseEntity.status(404).body(body);
        }
        // ????????? accesstoken??? ????????? 4000 ????????? ??????.
        if(requestHeader.get("accesstoken") == null) {
            body.put("code", 4000);
            return ResponseEntity.badRequest().body(body);
        }

        // ????????? ???????????? ????????? ????????? ????????? ????????? ??????.
        Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));

        // ????????? ??????????????? ?????? ????????? ????????????.
        if(checkToken.get("email") != null) {
            User user = userService.FindUserUseEmail((String)checkToken.get("email"));
            // ???????????? ?????? email??? DB??? ???????????? ????????? 4000????????? ??????.
            if(user == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }
            long commentId = -1;
            for(Comment c : board.getComments()) {
                if(c.getUserId().getId() == user.getId()) {
                    commentId = c.getId();
                    break;
                }
            }
            // commentId??? ????????? ???????????? ?????? ????????? ????????? ???????????? ?????? ?????????. ???????????? 4106 ??????.
            if(commentId == -1) {
                body.put("code", 4016);
                return ResponseEntity.badRequest().body(body);
            }
            // comment ????????? ??????.
            commentRepository.ModifyComment(commentModifyDTO, commentId);
            body.put("code", 2000);
            return ResponseEntity.status(201).body(body);
        }
        else {
            return ResponseEntity.status(401).body(checkToken);
        }
    }


    // Comment??? ???????????? ????????? ?????? ????????? ??????
    public HashMap<String, Object> DeleteComments(long commentId, User user) {
        body = new HashMap<>();
        Comment comment = commentRepository.FindCommentById(commentId);
        // comment??? ????????? 4405 ????????? ??????.
        if(comment == null) {
            body.put("code", 4405);
            return body;
        }

        // ?????? ????????? ???????????? ?????????
        if(!user.isAdminRole()) {
            // ????????? ?????? ???????????? ????????? ????????? ?????? ???
            if(user.getId() != comment.getUserId().getId()) {
                body.put("code", 4015);
                return body;
            }
        }

        // ?????? id??? ?????? comment??? ????????? ?????? ????????? ????????????.
        commentRepository.DeleteComment(commentId);
        body.put("code", 2000);

        return body;
    }

    // Comment??? ???????????? ????????? ?????? ????????? ??????
    public ResponseEntity<?> AddReport(CommentReportInputDTO commentReportInputDTO, Map<String, String> requestHeader) {
        body = new HashMap<>();
        // ????????? accesstoken??? ????????? 4000 ????????? ??????.
        if(requestHeader.get("accesstoken") == null) {
            body.put("code", 4000);
            return ResponseEntity.badRequest().body(body);
        }

        // ?????? ????????? id??? ?????? ???????????? ???????????? ????????????, 4405 ??????
        if(commentRepository.FindCommentById(commentReportInputDTO.getCommentId()) == null) {
            body.put("code", 4405);
            return ResponseEntity.status(404).body(body);
        }
        // ?????? ????????? id??? ?????? ??????????????? ????????????. ?????? ??????????????? 4005 ??????
        if(commentReportRepository.FindByCommentId(commentReportInputDTO.getCommentId()) != null) {
            body.put("code", 4005);
            return ResponseEntity.badRequest().body(body);
        }

        // ????????? ???????????? ????????? ????????? ????????? ????????? ??????.
        Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));
        // token??? email????????? ????????? ?????? ?????? ????????? ????????????
        if(checkToken.get("email") != null) {
            User user = userService.FindUserUseEmail((String) checkToken.get("email"));
            // ???????????? ?????? email??? DB??? ???????????? ????????? 4000????????? ??????.
            if(user == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }

            commentReportRepository.CreateCommentReport(commentReportInputDTO.getBody()
                    ,commentRepository.FindCommentById(commentReportInputDTO.getCommentId())
                    ,user);
            body.put("code", 2000);
            return ResponseEntity.status(201).body(body);
        }
        else {
            return ResponseEntity.status(401).body(checkToken);
        }
    }

    // ??????????????? ???????????? ????????? ?????? ????????? ??????
    public ResponseEntity<?> RemoveReport(CommentReportDeleteDTO commentReportDeleteDTO, Map<String, String> requestHeader) {
        body = new HashMap<>();
        // ????????? accesstoken??? ????????? 4000 ????????? ??????.
        if(requestHeader.get("accesstoken") == null) {
            body.put("code", 4000);
            return ResponseEntity.badRequest().body(body);
        }

        // ????????? ???????????? ????????? ????????? ????????? ????????? ??????.
        Map<String, Object> checkToken = userService.CheckToken(requestHeader.get("accesstoken"));

        // token??? email????????? ????????? ?????? ?????? ????????? ????????????
        if(checkToken.get("email") != null) {
            User user = userService.FindUserUseEmail((String) checkToken.get("email"));
            // ???????????? ?????? email??? DB??? ???????????? ????????? 4000????????? ??????.
            if(user == null) {
                body.put("code", 4000);
                return ResponseEntity.badRequest().body(body);
            }
            // ?????? ????????? ???????????? ???????????? ?????? ??? ?????? ????????? 4300 ????????? ??????.
            if(!user.isAdminRole()) {
                body.put("code", 4300);
                return ResponseEntity.status(403).body(body);
            }

            // report id??? ???????????? ????????? ????????? ??? ??? ?????? ????????? ???????????? ????????? 4406 ????????? ??????.
            if(commentReportRepository.FindById(commentReportDeleteDTO.getReportId()) == null) {
                body.put("code", 4406);
                return ResponseEntity.status(404).body(body);
            }

            commentReportRepository.DeleteCommentReport(commentReportDeleteDTO.getReportId());
            body.put("code", 2000);
            return ResponseEntity.status(200).body(body);
        }
        else {
            return ResponseEntity.status(401).body(checkToken);
        }
    }
}
