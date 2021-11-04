package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Common.SearchTypes;
import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.BoardGenre;
import com.IndieAn.GoFundIndie.Domain.Entity.BoardLike;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.CreateBoardCompleteDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager entityManager;

    public Board findBoardId(long id) {
        try {
            return entityManager.find(Board.class, id);
        } catch (NullPointerException | IllegalArgumentException e) {
            return null;
        }
    }

    public List<Board> findBoards(boolean isApprove) {
        return entityManager.createQuery(
                "SELECT a FROM Board a WHERE isApprove=" + isApprove + "", Board.class
        ).getResultList();
    }

    public List<Board> findAllBoards() {
        return entityManager.createQuery(
                "SELECT a FROM Board a", Board.class
        ).getResultList();
    }

    public List<Board> findBoardsByLike(User user) {
        return user.getBoardLikes().stream()
                .map(BoardLike::getBoardId).collect(Collectors.toList());
    }

    public List<Board> findBoardsByGenre(SearchTypes type) {
        int a = Arrays.asList(SearchTypes.values()).indexOf(type) + 1;
        return entityManager.createQuery(
                "SELECT a FROM BoardGenre a WHERE a.genreId=" + a, BoardGenre.class
        ).getResultList().stream()
                .map(BoardGenre::getBoardId)
                .filter(Board::isApprove)
                .collect(Collectors.toList());
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

        entityManager.persist(board);
        entityManager.flush();
        entityManager.close();
    }
}
