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
public class BoardLikeRepository {
    private final EntityManager entityManager;
    private final EntityManagerExtend entityManagerExtend;

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
            entityManagerExtend.singlePersist(bl);
        } else {
            entityManagerExtend.listRemove(list);
        }
    }
}

