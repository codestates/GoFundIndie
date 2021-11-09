package com.IndieAn.GoFundIndie.Repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EntityManagerExtend {
    private final EntityManager em;

    public void end() {
        em.flush();
        em.close();
    }

    public void singlePersist(Object e) {
        em.persist(e);
        end();
    }

    public void singleRemove(Object e) {
        em.remove(e);
        end();
    }

    public void listPersist(List<?> eList) {
        eList.forEach(em::persist);
        end();
    }

    public void listRemove(List<?> eList) {
        eList.forEach(em::remove);
        end();
    }
}