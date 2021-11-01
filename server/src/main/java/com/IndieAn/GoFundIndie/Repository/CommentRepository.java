package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.DTO.CommentInputDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.Comment;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class CommentRepository {
    private  final EntityManager entityManager;

    @Autowired
    public CommentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // DB Comment 테이블의 모든 comment 정보를 리턴한다.
    public List<Comment> FindCommentList() {
        return entityManager.createQuery("SELECT c FROM Comment as c", Comment.class).getResultList();
    }

    // DB Comment 테이블에서 해당 board id를 가진 코멘트들을 리턴한다.
    public List<Comment> FindCommentListByBoardId(long boardId) {
        return entityManager.createQuery("SELECT c FROM Comment as c where c.board_id = '"+ boardId +"'", Comment.class).getResultList();
    }

    // DB Comment 테이블에 매개변수 commentInputDTO의 데이터를 사용하여 Comment 정보를 저장한다.
    public Comment AddComment(CommentInputDTO commentInputDTO) {
        Comment comment = new Comment();
        User user = entityManager.find(User.class, commentInputDTO.getUserId());
        Board board = entityManager.find(Board.class, commentInputDTO.getBoardId());

        comment.setRating(commentInputDTO.getRating());
        comment.setUserId(user);
        comment.setBoardId(board);
        if(commentInputDTO.getDonation() > 0) comment.setDonation(commentInputDTO.getDonation());
        comment.setBody(commentInputDTO.getCommentBody());
        comment.setCreatedAt(new Date());
        comment.setSpoiler(commentInputDTO.isSpoiler());

        entityManager.persist(comment);

        entityManager.flush();
        entityManager.close();

        return comment;
    }
}
