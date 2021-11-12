package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.SearchBoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class BoardSearchRepository {
    private final EntityManager entityManager;

    private final String SELECT_SearchBoardDTO = "SELECT new com.IndieAn.GoFundIndie.Resolvers.DTO.Board.SearchBoardDTO" +
                                                 "(b.id, b.title, b.posterImg) ";

    public List<SearchBoardDTO> SearchBoardsConsonant(String start, String end, int limit) {
        return entityManager.createQuery(
            SELECT_SearchBoardDTO +
                    "FROM Board b " +
                    "WHERE b.isApprove = true " +
                    "AND b.title >= '" + start + "' " +
                    "AND b.title <= '" + end + "' " +
                    "ORDER BY b.title", SearchBoardDTO.class
        ).setMaxResults(limit).getResultList();
    }

    public List<SearchBoardDTO> SearchBoards(String str, int limit) {
        return entityManager.createQuery(
                SELECT_SearchBoardDTO +
                        "FROM Board b " +
                        "WHERE b.isApprove = true " +
                        "AND b.title like '%" + str + "%' " +
                        "ORDER BY b.title", SearchBoardDTO.class
        ).setMaxResults(limit).getResultList();
    }

    public List<SearchBoardDTO> SearchBoardsFromNull(int limit) {
        return entityManager.createQuery(
            SELECT_SearchBoardDTO +
                    "FROM Board b " +
                    "WHERE b.isApprove = true " +
                    "ORDER BY b.commentAmount", SearchBoardDTO.class
        ).setMaxResults(limit).getResultList();
    }
}
