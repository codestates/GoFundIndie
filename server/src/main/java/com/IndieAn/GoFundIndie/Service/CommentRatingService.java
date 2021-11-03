package com.IndieAn.GoFundIndie.Service;

import com.IndieAn.GoFundIndie.Domain.Entity.CommentRating;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.CommentRatingRepository;
import com.IndieAn.GoFundIndie.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CommentRatingService {
    private final CommentRatingRepository commentRatingRepository;
    private final CommentRepository commentRepository;
    private HashMap<String, Object> body = new HashMap<>();

    @Autowired
    public CommentRatingService(CommentRatingRepository commentRatingRepository, CommentRepository commentRepository) {
        this.commentRatingRepository = commentRatingRepository;
        this.commentRepository = commentRepository;
    }

    public HashMap<String, Object> addRating(User user, long commentId) {
        body.clear();
        // 존재하지 않는 코멘트id
        if(commentRepository.FindCommentById(commentId) == null) {
            body.put("code", 4405);
            return body;
        }

        // 해당 유저가 코멘트에 좋아요를 했는지 레이팅 테이블의 존재를 확인한다.
        CommentRating commentRating = commentRatingRepository.FindRatingByUserAndComment(user.getId(), commentId);

        // 존재하지 않으면 좋아요를 수행하는 것이다.
        // CommentRating Table에 추가한다.
        if(commentRating == null) {
            commentRatingRepository.CreateRating(user.getId(), commentId);
        }
        // 그것이 아니라면 좋아요를 취소하는 것이다.
        // CommentRating Table에서 삭제한다.
        else {
            commentRatingRepository.DeleteRating(commentRating.getId());
        }
        body.put("code", 2000);
        return body;
    }
}
