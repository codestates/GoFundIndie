package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.Casting;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.CreateCastingCompleteDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.PutCastingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class CastingRepository {
    private final EntityManager entityManager;

    private final AtomicBoolean isChange = new AtomicBoolean(false);

    public long RegisterTempCasting(Board board) {
        Casting casting = new Casting();
        casting.setBoardId(board);

        entityManager.persist(casting);
        entityManager.flush();
        entityManager.close();

        return casting.getId();
    }

    public long CompleteCasting(Casting casting, CreateCastingCompleteDTO dto) {
        casting.setName(dto.getName());
        casting.setPosition(dto.getPosition());

        entityManager.persist(casting);
        entityManager.flush();
        entityManager.close();

        return casting.getId();
    }

    public long PutCasting(Casting casting, PutCastingDTO dto) {
        isChange.set(false);

        if(dto.getName() != null) {
            casting.setName(dto.getName());
            isChange.set(true);
        }
        if(dto.getPosition() != 0) {
            casting.setPosition(dto.getPosition());
            isChange.set(true);
        }
        if(dto.getImage() != null) {
            casting.setImage(dto.getImage());
            isChange.set(true);
        }

        if(isChange.get()){
            entityManager.persist(casting);
            entityManager.flush();
            entityManager.close();
        }

        return casting.getId();
    }

    public void updateCastingImage(Casting casting, String image) {
        casting.setImage(image);
        entityManager.persist(casting);
        entityManager.flush();
        entityManager.close();
    }

    public Casting findCastingById(Long id) {
        try {
            return entityManager.find(Casting.class, id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void RemoveCasting(Casting casting) {
        entityManager.remove(casting);
        entityManager.flush();
        entityManager.close();
    }
}
