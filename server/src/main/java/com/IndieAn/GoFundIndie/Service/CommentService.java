package com.IndieAn.GoFundIndie.Service;

import com.IndieAn.GoFundIndie.Domain.DTO.CommentInputDTO;
import com.IndieAn.GoFundIndie.Domain.DTO.CommentOutputDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.Comment;
import com.IndieAn.GoFundIndie.Domain.Entity.CommentRating;
import com.IndieAn.GoFundIndie.Repository.CommentRepository;
import com.IndieAn.GoFundIndie.Repository.JPAInterface.CommentJPAInterface;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentJPAInterface commentJPAInterface;
    private HashMap<String, Object> body = null;

    @Autowired
    public CommentService(CommentRepository commentRepository, CommentJPAInterface commentJPAInterface) {
        this.commentRepository = commentRepository;
        this.commentJPAInterface = commentJPAInterface;
    }

    // Comment를 생성하는 기능을 하는 서비스 기능
    public Comment AddCommentData(CommentInputDTO commentInputDTO) {
        // comment들 중에 한 board id 에 한 user id가 있는지 확인한다. 있다면 이미 comment를 작성한 것이기 때문에 null을 리턴
        for(Comment c : commentRepository.FindCommentList()) {
            if(c.getBoardId().getId() == commentInputDTO.getBoardId() && c.getUserId().getId() == commentInputDTO.getUserId()) {
                return null;
            }
        }
        return commentRepository.AddComment(commentInputDTO);
    }

    // 각 보드에 대한 Comment들을 불러오는 서비스 기능
    public HashMap<String, Object> GetCommentPage(Long boardId, Pageable pageable) {
        body = new HashMap<>();
        Board board = commentRepository.FindBoardDB(boardId);
        // board가 null이라면 코드 4401 응답을 낸다.
        if(board == null) {
            body.put("code", 4401);
            return body;
        }

        // 페이징을 통해 응답과 합께 데이터를 보내준다.
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
                });

        body.put("code", 2000);
        body.put("data", commentList);
        return body;
    }
}
