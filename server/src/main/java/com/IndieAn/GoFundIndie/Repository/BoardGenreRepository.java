package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.BoardGenre;
import com.IndieAn.GoFundIndie.Domain.Entity.Genre;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class BoardGenreRepository extends EntityManagerExtend{
    private final EntityManager entityManager;

    private String queryGenerator (long boardId, long genreId) {
        return "SELECT a " +
               "FROM BoardGenre AS a " +
               "WHERE a.boardId = " + boardId +
               " AND a.genreId = " + genreId + "";
    }

    public void CreateLink(Board board, Genre genre) {
        if(entityManager.createQuery(queryGenerator(board.getId(), genre.getId()), BoardGenre.class)
                .getResultList().size() == 0){
            BoardGenre bg = new BoardGenre();
            bg.setBoardId(board);
            bg.setGenreId(genre);

            singlePersist(bg, entityManager);
        }
    }

    public void DisLink(long boardId, long genreId) {
        try {
            entityManager.createQuery(queryGenerator(boardId, genreId), BoardGenre.class)
                    .getResultList()
                    .forEach(el -> {
                        if(el != null) entityManager.remove(el);
                    });

            end(entityManager);
        } catch (IllegalArgumentException e) {
            log.info("DisLink query is illegality");
        }
    }
}
