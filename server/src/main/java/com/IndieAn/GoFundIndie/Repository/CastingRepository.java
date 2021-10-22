package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.Casting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class CastingRepository {
    private final EntityManager entityManager;

    public void addCastingInfo(Board board, String image) {
        Casting casting = new Casting();
        casting.setBoardId(board);
        casting.setImage(image);
        entityManager.persist(casting);
        entityManager.flush();
        entityManager.close();
    }

    public Casting findCastingById(Long id){
        try {
            return entityManager.find(Casting.class, id);
        } catch (NullPointerException e) {
            return null;
        }
    }
}
