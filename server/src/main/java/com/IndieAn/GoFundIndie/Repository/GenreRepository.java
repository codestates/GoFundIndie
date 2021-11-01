package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.Genre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class GenreRepository {
    private final EntityManager entityManager;

    public Genre FindById(Long id) {
        return entityManager.find(Genre.class, id);
    }

    public List<Genre> FindAll() {
        return entityManager.createQuery(
                "SELECT a FROM Genre a", Genre.class)
                .getResultList();
    }

    public void RegisterDatabase(Genre genre) {
        entityManager.persist(genre);
        entityManager.flush();
        entityManager.close();
    }

    public boolean Delete(Long id) {
        List<Genre> list = entityManager.createQuery(
                "SELECT el FROM Genre el WHERE id=" + id + "", Genre.class)
                .getResultList();
        if(list.size() == 0) return false;
        entityManager.remove(list.get(0));
        entityManager.flush();
        entityManager.close();
        return true;
    }
}