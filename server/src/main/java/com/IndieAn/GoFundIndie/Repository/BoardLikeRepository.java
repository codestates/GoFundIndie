package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.BoardLike;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class BoardLikeRepository extends EntityManagerExtend{
    private final EntityManager entityManager;

    public boolean isLikedBoard(User user, Board board) {
        return entityManager.createQuery(
            "SELECT bl " +
                    "FROM BoardLike bl " +
                    "WHERE bl.userId = " + user.getId() + " " +
                    "AND bl.boardId = " + board.getId() + "", BoardLike.class
        ).getResultList().size() > 0;
    }

    public void LikeBoardSwitch(User user, Board board) {
        List<BoardLike> list = entityManager.createQuery(
        "SELECT bl " +
                "FROM BoardLike bl " +
                "WHERE bl.userId = " + user.getId() + " " +
                "AND bl.boardId = " + board.getId() + "", BoardLike.class
        ).getResultList();

        if(list.size() == 0) {
            BoardLike bl = new BoardLike();
            bl.setBoardId(board);
            bl.setUserId(user);
            bl.setCreatedAt(new Date());
            board.setLikeAmount(board.getLikeAmount() + 1);
            entityManager.persist(board);
            singlePersist(bl, entityManager);
        } else {
            board.setLikeAmount(board.getLikeAmount() - list.size());
            entityManager.persist(board);
            listRemove(list, entityManager);
        }
    }
}

