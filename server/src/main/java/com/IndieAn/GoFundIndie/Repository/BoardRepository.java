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

    public Board findBoardId(long id) {
        try {
            return entityManager.find(Board.class, id);
        } catch (NullPointerException | IllegalArgumentException e) {
            return null;
        }
    }

    public List<Board> findBoards(boolean isApprove, int limit) {
        return entityManager.createQuery(
                "SELECT a FROM Board a " +
                        "WHERE isApprove=" + isApprove + "", Board.class
        ).setMaxResults(limit).getResultList();
    }

    public List<Board> findAllBoards() {
        return entityManager.createQuery(
                "SELECT a FROM Board a", Board.class
        ).getResultList();
    }

    public List<Board> findBoardsByLike(User user, int limit) {
        return entityManager.createQuery(
            "SELECT b FROM BoardLike l " +
                    "LEFT JOIN l.boardId b " +
                    "WHERE l.userId = " + user.getId() + " AND b.isApprove = true", Board.class)
                .setMaxResults(limit)
                .getResultList();
    }

    // TODO Board 에 Comment 숫자 달기 -> 쿼리 한번으로 정렬까지 해결됨
    // TODO 필요한 정보만 있는 DTO 에 query return 받으면 변환 작업 없어도 되지 않나 -> 연관만 없으면 됨
    // TODO   -> 유저 정보는 admin 이 검색하는거 따로 만들어서 주자
    // TODO   -> 어드민이 관리 페이지나 다른거 볼떄만
    // TODO   -> 연관관계 최소화 해서 쿼리 덜 날리게끔
    public List<Board> findBoardsByGenre(SearchTypes type, int limit) {
        int genreId = Arrays.asList(SearchTypes.values()).indexOf(type) + 1;
        return entityManager.createQuery(
            "SELECT b FROM BoardGenre g " +
                    "LEFT JOIN g.boardId b " +
                    "WHERE g.genreId = " + genreId + " AND b.isApprove = true", Board.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<Board> findBoardsNew(int limit) {
        return entityManager.createQuery(
        "SELECT b FROM Board b " +
                "WHERE b.isApprove = true " +
                "ORDER BY b.id DESC"
        ,Board.class)
                .setMaxResults(limit)
                .getResultList();
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
