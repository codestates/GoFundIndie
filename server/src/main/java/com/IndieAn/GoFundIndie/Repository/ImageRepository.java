package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.Still;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Still.StillGraphQLDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class ImageRepository extends EntityManagerExtend{
    private final EntityManager entityManager;

    public void addStillInfo(Board board, String image) {
        Still still = new Still();
        still.setBoardId(board);
        still.setImage(image);
        singlePersist(still, entityManager);
    }

    public Still findStillById(Long id){
        try {
            return entityManager.find(Still.class, id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void deleteStill(Still still){
        singleRemove(still, entityManager);
    }

    public List<StillGraphQLDTO> findStillByBoard(long boardId) {
        return entityManager.createQuery(
            "SELECT new com.IndieAn.GoFundIndie.Resolvers.DTO.Still.StillGraphQLDTO" +
                    "(s.id, s.image) " +
                    "FROM Still s " +
                    "WHERE s.boardId = " + boardId + " ", StillGraphQLDTO.class
        ).getResultList();
    }
}
