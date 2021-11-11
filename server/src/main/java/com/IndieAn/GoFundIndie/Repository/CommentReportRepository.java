package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.Comment;
import com.IndieAn.GoFundIndie.Domain.Entity.CommentReport;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Transactional
@Repository
public class CommentReportRepository extends EntityManagerExtend{
    private final EntityManager entityManager;

    @Autowired
    public CommentReportRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // id를 통해서 comment report 정보를 찾아낸다.
    public CommentReport FindById(long reportId) {
        return entityManager.find(CommentReport.class, reportId);
    }

    // comment id를 통해 해당 신고내역이 있는지 확인하는 기능
    public CommentReport FindByCommentId(long commentId) {
        List<CommentReport> commentReportList = entityManager
                .createQuery("SELECT cr FROM CommentReport AS cr WHERE comment_id =" + commentId + "", CommentReport.class)
                .getResultList();
        if(commentReportList.size() == 0) return null;
        return commentReportList.get(0);
    }

    // comment report 정보를 DB에 저장
    public void CreateCommentReport(String body, Comment comment, User user) {
        CommentReport commentReport = new CommentReport();
        commentReport.setUserId(user);
        commentReport.setCommentId(comment);
        commentReport.setBody(body);

        entityManager.persist(commentReport);
        end(entityManager);
    }

    // comment report 정보를 DB에서 삭제
    public void DeleteCommentReport(long reportId) {
        CommentReport commentReport = entityManager.find(CommentReport.class, reportId);
        entityManager.remove(commentReport);

        end(entityManager);
    }
}
