package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.BoardReport;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Resolvers.DTO.BoardReport.BoardReportGraphQLDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class BoardReportRepository extends EntityManagerExtend {
    private final EntityManager entityManager;

    private final String SELECT_BoardReportGraphQLDTO = "SELECT new com.IndieAn.GoFundIndie.Resolvers.DTO.BoardReport.BoardReportGraphQLDTO" + "(br.id, b.id, b.title, r.id, r.nickname, d.id, d.nickname, br.body) ";

    public boolean ReportNew(User user, Board board, String body) {
        if(entityManager.createQuery(
                "SELECT br " +
                        "FROM BoardReport br " +
                        "WHERE br.userId = " + user.getId() + " " +
                        "AND br.boardId = " + board.getId(), BoardReport.class
        ).getResultList().size() == 0) {
            singlePersist(BoardReport.builder()
                    .userId(user)
                    .boardId(board)
                    .body(body)
                    .build(), entityManager);
            return true;
        } else {
            return false;
        }
    }

    public boolean DeleteReport(long id) {
        try {
            singleRemove(entityManager.find(BoardReport.class, id), entityManager);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public BoardReportGraphQLDTO FindReport(long id) {
        return entityManager.createQuery(
            SELECT_BoardReportGraphQLDTO +
                    "FROM BoardReport br " +
                    "JOIN br.userId r " +
                    "ON br.id = " + id + " " +
                    "JOIN br.boardId b " +
                    "JOIN b.userId d", BoardReportGraphQLDTO.class
        ).getSingleResult();
    }

    public List<BoardReportGraphQLDTO> FindReports() {
        return entityManager.createQuery(
            SELECT_BoardReportGraphQLDTO +
                    "FROM BoardReport br " +
                    "JOIN br.userId r " +
                    "JOIN br.boardId b " +
                    "JOIN b.userId d", BoardReportGraphQLDTO.class
        ).getResultList();
    }
}
