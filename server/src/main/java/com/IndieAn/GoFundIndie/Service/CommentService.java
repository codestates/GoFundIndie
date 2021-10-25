package com.IndieAn.GoFundIndie.Service;

import com.IndieAn.GoFundIndie.Domain.DTO.CommentInputDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.Comment;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
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
}
