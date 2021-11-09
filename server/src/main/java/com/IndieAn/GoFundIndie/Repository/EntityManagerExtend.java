package com.IndieAn.GoFundIndie.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EntityManagerExtend {
    public void end(EntityManager em) {
        em.flush();
        em.close();
    }

    public void singlePersist(Object e, EntityManager em) {
        em.persist(e);
        end(em);
    }

    public void singleRemove(Object e, EntityManager em) {
        em.remove(e);
        end(em);
    }

    public void listPersist(List<?> eList, EntityManager em) {
        eList.forEach(em::persist);
        end(em);
    }

    public void listRemove(List<?> eList, EntityManager em) {
        eList.forEach(em::remove);
        end(em);
    }
}