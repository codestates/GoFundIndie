package com.IndieAn.GoFundIndie.Service;

import com.IndieAn.GoFundIndie.Domain.DTO.CommentInputDTO;
import com.IndieAn.GoFundIndie.Domain.DTO.CommentOutputDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.Comment;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.CommentRatingRepository;
import com.IndieAn.GoFundIndie.Repository.CommentRepository;
import com.IndieAn.GoFundIndie.Repository.JPAInterface.CommentJPAInterface;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentJPAInterface commentJPAInterface;
    private final UserRepository userRepository;
    private final CommentRatingRepository commentRatingRepository;
    private HashMap<String, Object> body;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentJPAInterface commentJPAInterface,
                          UserRepository userRepository, CommentRatingRepository commentRatingRepository) {
        this.commentRepository = commentRepository;
        this.commentJPAInterface = commentJPAInterface;
        this.userRepository = userRepository;
        this.commentRatingRepository = commentRatingRepository;
    }

    // Comment를 생성하는 기능을 하는 서비스 기능
    public HashMap<String, Object> AddCommentData(CommentInputDTO commentInputDTO, User user) {
        body = new HashMap<>();

        // 토큰으로 얻은 email의 pk와 입력으로 들어온 user의 pk가 다르다면 400 응답을 한다.
        if(user.getId() != commentInputDTO.getUserId()) {
            body.put("code", 4014);
        }
        else {
            // comment들 중에 한 board id 에 한 user id가 있는지 확인한다. 있다면 이미 comment를 작성한 것이기 때문에 4004응답을 한다.
            for(Comment c : commentRepository.FindCommentList()) {
                if(c.getBoardId().getId() == commentInputDTO.getBoardId() && c.getUserId().getId() == commentInputDTO.getUserId()) {
                    body.put("code", 4004);
                    return body;
                }
            }
            // comment 작성을 한다.
            commentRepository.AddComment(commentInputDTO);
            body.put("code", 2000);
        }
        return body;
    }

    // 각 보드에 대한 Comment들을 불러오는 서비스 기능
    public HashMap<String, Object> GetCommentPage(Board board, String email, String type, Integer page) {
        body = new HashMap<>();

        // email이 들어와 회원인 경우
        User user = null;
        if(email != null) {
            user = userRepository.FindUserByEmail(email);
        }
        // 페이징을 통해 응답과 합께 데이터를 보내준다.
        if(page == null) page = 1;
        if(type == null) type = "new";
        // 인기순이면 order가 like가 되고, 그 외에는 id로 최신순으로 정렬하도록 한다.
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

    // Comment를 삭제하는 기능을 하는 서비스 기능
    public HashMap<String, Object> DeleteComments(long commentId, User user) {
        body = new HashMap<>();
        Comment comment = commentRepository.FindCommentById(commentId);
        // comment가 없다면 4405 응답을 한다.
        if(comment == null) {
            body.put("code", 4405);
            return body;
        }

        // 해당 유저가 관리자가 아니면
        if(!user.isAdminRole()) {
            // 유저가 해당 코멘트를 작성한 유저가 아닐 때
            if(user.getId() != comment.getUserId().getId()) {
                body.put("code", 4015);
                return body;
            }
        }

        // 해당 id를 가진 comment가 있다면 삭제 과정을 수행한다.
        commentRepository.DeleteComment(commentId);
        body.put("code", 2000);

        return body;
    }
}
