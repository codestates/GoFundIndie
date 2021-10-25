package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.Still;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@Transactional
@RequiredArgsConstructor
public class ImageRepository {
    private final EntityManager entityManager;

    public void addStillInfo(Board board, String image) {
        Still still = new Still();
        still.setBoardId(board);
        still.setImage(image);
        entityManager.persist(still);
        entityManager.flush();
        entityManager.close();
    }

    public Still findStillById(Long id){
        try {
            return entityManager.find(Still.class, id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void deleteStill(Still still){
        entityManager.remove(still);
        entityManager.flush();
        entityManager.close();
    }
}
