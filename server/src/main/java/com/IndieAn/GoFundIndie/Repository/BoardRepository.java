package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.CreateBoardCompleteDTO;
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
public class BoardRepository {
    private final EntityManager entityManager;

    public Board findBoardId(long id) {
        try {
            return entityManager.find(Board.class, id);
        } catch (NullPointerException | IllegalArgumentException e) {
            return null;
        }
    }

    public List<Board> findBoards() {
        return entityManager.createQuery(
                "SELECT a FROM Board a", Board.class
        ).getResultList();
    }

    public void updateBoardImg(Board board, String img) {
        board.setPosterImg(img);
        entityManager.persist(board);
        entityManager.flush();
        entityManager.close();
    }

    // Create Temp Board
    public long RegisterTempBoard(User user) {
        Board board = new Board();
        board.setUserId(user);
        board.setInfoCountry("TEMP");
        entityManager.persist(board);
        entityManager.flush();
        entityManager.close();
        return board.getId();
    }

    public long CompleteBoard(CreateBoardCompleteDTO dto) {
        return 0;
    }
}
