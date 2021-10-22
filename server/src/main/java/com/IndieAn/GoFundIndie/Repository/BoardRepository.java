package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager entityManager;

    public Board findBoardId(long id) {
        try {
            return entityManager.find(Board.class, id);
        } catch (NullPointerException | IllegalArgumentException e) {
            return null;
        }
    }
}
