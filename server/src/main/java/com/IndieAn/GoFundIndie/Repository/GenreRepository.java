package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.Entity.Genre;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class GenreRepository extends EntityManagerExtend{
    private final EntityManager entityManager;

    public Genre FindById(Long id) {
        if(id == null) return null;
        try {
            return entityManager.find(Genre.class, id);
        } catch (NullPointerException | IllegalArgumentException e) {
            return null;
        }
    }

    public List<Genre> FindAll() {
        return entityManager.createQuery(
                "SELECT a FROM Genre a", Genre.class)
                .getResultList();
    }

    public void RegisterDatabase(Genre genre) {
        singlePersist(genre, entityManager);
    }

    public boolean Delete(Long id) {
        List<Genre> list = entityManager.createQuery(
                "SELECT el FROM Genre el WHERE id=" + id + "", Genre.class)
                .getResultList();
        if(list.size() == 0) return false;
        singleRemove(list.get(0), entityManager);
        return true;
    }

    public List<GenreGraphQLDTO> findGenreByBoard(long boardId) {
        return entityManager.createQuery(
                "SELECT DISTINCT new com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO" +
                        "(g.id, g.name) " +
                        "FROM BoardGenre bg " +
                        "JOIN bg.genreId g " +
                        "ON bg.boardId = " + boardId + " ", GenreGraphQLDTO.class
        ).getResultList();
    }
}