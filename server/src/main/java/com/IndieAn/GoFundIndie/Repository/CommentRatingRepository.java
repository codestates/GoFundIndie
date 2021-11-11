package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.Comment;
import com.IndieAn.GoFundIndie.Domain.Entity.CommentRating;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class CommentRatingRepository extends EntityManagerExtend{
    private final EntityManager entityManager;

    @Autowired
    public CommentRatingRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CommentRating FindRatingByUserAndComment(long userId, long commentId) {
        List<CommentRating> commentRatingList = entityManager
                .createQuery("SELECT cr FROM CommentRating cr " +
                        "WHERE user_id =" + userId + " AND comment_id =" + commentId + "", CommentRating.class)
                .getResultList();
        if(commentRatingList.size() == 0) return null;
        return commentRatingList.get(0);
    }

    public boolean commentRatedCheck(long userId, long commentId) {
        return entityManager.createQuery(
                "SELECT cr " +
                        "FROM CommentRating cr " +
                        "WHERE cr.userId = " + userId + " " +
                        "AND cr.commentId = " + commentId + "", CommentRating.class
        ).getResultList().size() != 0;
    }

    // DB CommentRating 테이블에 userId와 commentId를 사용해 CommentRating 정보를 저장한다.
    public CommentRating CreateRating(long userId, long commentId) {
        CommentRating commentRating = new CommentRating();
        User user = entityManager.find(User.class, userId);
        Comment comment = entityManager.find(Comment.class, commentId);
        comment.setLike(comment.getLike()+1);

        commentRating.setUserId(user);
        commentRating.setCommentId(comment);

        entityManager.persist(commentRating);

        end(entityManager);

        return commentRating;
    }

    // DB CommentRating 테이블에 id를 사용해 CommentRating 정보를 삭제한다.
    public CommentRating DeleteRating(long id) {
        CommentRating deleteRating = entityManager.find(CommentRating.class, id);
        Comment comment = entityManager.find(Comment.class, deleteRating.getCommentId().getId());
        comment.setLike(comment.getLike()-1);
        entityManager.remove(deleteRating);

        end(entityManager);

        return deleteRating;
    }
}
