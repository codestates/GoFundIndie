package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.Casting;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.CastingGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.CreateCastingCompleteDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.PutCastingDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Comment.CommentGraphQLDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class CastingRepository extends EntityManagerExtend{
    private final EntityManager entityManager;

    private final AtomicBoolean isChange = new AtomicBoolean(false);

    public long RegisterTempCasting(Board board) {
        Casting casting = new Casting();
        casting.setBoardId(board);

        singlePersist(casting, entityManager);

        return casting.getId();
    }

    public long CompleteCasting(Casting casting, CreateCastingCompleteDTO dto) {
        casting.setName(dto.getName());
        casting.setPosition(dto.getPosition());

        singlePersist(casting, entityManager);

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
            singlePersist(casting, entityManager);
        }

        return casting.getId();
    }

    public void updateCastingImage(Casting casting, String image) {
        casting.setImage(image);
        singlePersist(casting, entityManager);
    }

    public Casting findCastingById(Long id) {
        try {
            return entityManager.find(Casting.class, id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void RemoveCasting(Casting casting) {
        singleRemove(casting, entityManager);
    }

    public List<CastingGraphQLDTO> findCastingByBoard(long boardId) {
        return entityManager.createQuery(
                "SELECT DISTINCT new com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.CastingGraphQLDTO" +
                        "(c.id, c.name, c.position, c.image) " +
                        "FROM Casting c " +
                        "JOIN c.boardId b " +
                        "ON c.boardId = " + boardId + " " +
                        "ORDER BY c.position", CastingGraphQLDTO.class
        ).getResultList();
    }
}
