package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Common.SearchTypes;
import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.BoardGenre;
import com.IndieAn.GoFundIndie.Domain.Entity.BoardLike;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.BoardGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.CreateBoardCompleteDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager entityManager;

    private final static String BOARD_GRAPHQL_DTO_QUERY_SELECT = "SELECT new com.IndieAn.GoFundIndie.Resolvers.DTO.Board.BoardGraphQLDTO(b.id, b.isApprove, b.title, b.posterImg, b.infoCountry, b.infoCreatedYear, b.infoCreatedDate, b.infoTime, b.infoLimit) ";

    public Board findBoardId(Long id) {
        if(id == null) return null;
        try {
            return entityManager.find(Board.class, id);
        } catch (NullPointerException | IllegalArgumentException e) {
            return null;
        }
    }

    public List<BoardGraphQLDTO> findBoards(boolean isApprove, int limit) {
        return entityManager.createQuery(
            BOARD_GRAPHQL_DTO_QUERY_SELECT +
                    "FROM Board b " +
                    "WHERE b.isApprove=" + isApprove + " " +
                    "ORDER BY b.commentAmount DESC", BoardGraphQLDTO.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<BoardGraphQLDTO> findAllBoards(int limit) {
        return entityManager.createQuery(
            BOARD_GRAPHQL_DTO_QUERY_SELECT +
                    "FROM Board b " +
                    "ORDER BY b.id DESC", BoardGraphQLDTO.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<BoardGraphQLDTO> findBoardsByLike(User user, int limit) {
        return entityManager.createQuery(
            BOARD_GRAPHQL_DTO_QUERY_SELECT +
                    "FROM BoardLike l " +
                    "LEFT JOIN l.boardId b " +
                    "WHERE l.userId = " + user.getId() + " AND b.isApprove = true " +
                    "ORDER BY l.createdAt DESC", BoardGraphQLDTO.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<BoardGraphQLDTO> findBoardsByGenre(SearchTypes type, int limit) {
        int genreId = Arrays.asList(SearchTypes.values()).indexOf(type) + 1;
        return entityManager.createQuery(
            BOARD_GRAPHQL_DTO_QUERY_SELECT +
                    "FROM BoardGenre g " +
                    "LEFT JOIN g.boardId b " +
                    "WHERE g.genreId = " + genreId + " AND b.isApprove = true " +
                    "ORDER BY b.commentAmount DESC", BoardGraphQLDTO.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<BoardGraphQLDTO> findBoardsNew(int limit) {
        return entityManager.createQuery(
            BOARD_GRAPHQL_DTO_QUERY_SELECT +
                    "FROM Board b " +
                    "WHERE b.isApprove = true " +
                    "ORDER BY b.createdAt DESC", BoardGraphQLDTO.class)
                .setMaxResults(limit).getResultList();
    }

    // Upload or Update poster image
    public void updateBoardImg(Board board, String img) {
        board.setPosterImg(img);
        entityManager.persist(board);
        entityManager.flush();
        entityManager.close();
    }

    // Create Temp Board
    public long RegisterTempBoard(User user) {
        Board board = new Board();

        board.setUserId(user);
        board.setInfoCountry("TEMP");

        entityManager.persist(board);
        entityManager.flush();
        entityManager.close();

        return board.getId();
    }

    // Put Complete Board
    public long CompleteBoard(Board board, CreateBoardCompleteDTO dto) {
        // not null values
        board.setTitle(dto.getTitle());
        board.setInfoCountry(dto.getInfoCountry());
        board.setInfoCreatedYear(dto.getInfoCreatedYear());
        board.setInfoTime(dto.getInfoTime());
        board.setInfoStory(dto.getInfoStory());

        // nullable values
        board.setProducer(dto.getProducer());
        board.setDistributor(dto.getDistributor());
        board.setPosterImg(dto.getPosterImg());
        board.setViewLink(dto.getViewLink());
        board.setInfoLimit(dto.getInfoLimit());
        board.setInfoSubtitle(dto.isInfoSubtitle());
        board.setInfoCreatedDate(dto.getInfoCreatedDate());

        entityManager.persist(board);
        entityManager.flush();
        entityManager.close();

        return board.getId();
    }

    public void ApproveBoard(Board board) {
        board.setApprove(true);
        board.setCreatedAt(new Date());

        entityManager.persist(board);
        entityManager.flush();
        entityManager.close();
    }
}
