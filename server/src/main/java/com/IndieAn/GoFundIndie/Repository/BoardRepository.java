package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Common.SearchTypes;
import com.IndieAn.GoFundIndie.Domain.Entity.*;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class BoardRepository extends EntityManagerExtend{
    private final EntityManager entityManager;

    private final String SELECT_BoardGraphQLDTO = "SELECT new com.IndieAn.GoFundIndie.Resolvers.DTO.Board.BoardGraphQLDTO" +
                                                  "(b.id, b.isApprove, b.title, b.posterImg, b.infoCountry, b.infoCreatedYear, b.infoCreatedDate, b.infoTime, b.infoLimit) ";

    public Board findBoardId(Long id) {
        if(id == null) return null;
        try {
            return entityManager.find(Board.class, id);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public List<BoardGraphQLDTO> findBoards(boolean isApprove, int limit) {
        return entityManager.createQuery(
            SELECT_BoardGraphQLDTO +
                    "FROM Board b " +
                    "WHERE b.isApprove=" + isApprove + " " +
                    "ORDER BY b.commentAmount DESC", BoardGraphQLDTO.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<BoardGraphQLDTO> findAllBoards(int limit) {
        return entityManager.createQuery(
            SELECT_BoardGraphQLDTO +
                    "FROM Board b " +
                    "ORDER BY b.id DESC", BoardGraphQLDTO.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<BoardGraphQLDTO> findBoardsLike(User user, int limit) {
        return entityManager.createQuery(
            SELECT_BoardGraphQLDTO +
                    "FROM BoardLike l " +
                    "JOIN l.boardId b " +
                    "ON l.userId = " + user.getId() + " " +
                    "WHERE b.isApprove = true " +
                    "ORDER BY l.createdAt DESC", BoardGraphQLDTO.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<LikeBoardGraphQLDTO> findBoardsMyLike(User user, int limit) {
        return entityManager.createQuery(
            "SELECT new com.IndieAn.GoFundIndie.Resolvers.DTO.Board.LikeBoardGraphQLDTO" +
                    "(b, b.isApprove, b.title, b.posterImg, b.infoCountry, b.infoCreatedYear, b.infoCreatedDate, b.infoTime, b.infoLimit, b.infoStory) " +
                    "FROM BoardLike l " +
                    "JOIN l.boardId b " +
                    "ON l.userId = " + user.getId() + " " +
                    "WHERE b.isApprove = true " +
                    "ORDER BY l.createdAt DESC", LikeBoardGraphQLDTO.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<BoardGraphQLDTO> findBoardsDonation(User user, int limit) {
        return entityManager.createQuery(
                        SELECT_BoardGraphQLDTO +
                                "FROM Comment c " +
                                "JOIN c.boardId b " +
                                "ON c.userId = " + user.getId() + " " +
                                "WHERE c.donation > 0 " +
                                "ORDER BY c.createdAt DESC", BoardGraphQLDTO.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<DonationBoardGraphQLDTO> findBoardsMyDonation(User user, int limit) {
        return entityManager.createQuery(
            "SELECT new com.IndieAn.GoFundIndie.Resolvers.DTO.Board.DonationBoardGraphQLDTO" +
                    "(b.id, b.isApprove, b.title, b.posterImg, b.infoCreatedYear, b.infoCreatedDate, c.donation, c.createdAt) " +
                    "FROM Comment c " +
                    "JOIN c.boardId b " +
                    "ON c.userId = " + user.getId() + " " +
                    "WHERE c.donation > 0 " +
                    "ORDER BY c.createdAt DESC", DonationBoardGraphQLDTO.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<BoardGraphQLDTO> findBoardsByGenre(SearchTypes type, int limit) {
        int genreId = Arrays.asList(SearchTypes.values()).indexOf(type) + 1;
        return entityManager.createQuery(
            SELECT_BoardGraphQLDTO +
                    "FROM BoardGenre g " +
                    "LEFT JOIN g.boardId b " +
                    "ON g.genreId = " + genreId + " " +
                    "WHERE b.isApprove = true " +
                    "ORDER BY b.commentAmount DESC", BoardGraphQLDTO.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<BoardGraphQLDTO> findBoardsNew(int limit) {
        return entityManager.createQuery(
            SELECT_BoardGraphQLDTO +
                    "FROM Board b " +
                    "WHERE b.isApprove = true " +
                    "ORDER BY b.createdAt DESC", BoardGraphQLDTO.class)
                .setMaxResults(limit).getResultList();
    }

    public List<BoardGraphQLDTO> findBoardsRandom(int limit) {
        List<BoardGraphQLDTO> list = findBoards(true, Integer.MAX_VALUE - 1);
        Collections.shuffle(list);
        return list.subList(0, limit);
    }

    public List<BoardGraphQLDTO> findBoardsSeoul2020(int limit) {
        return entityManager.createQuery(
            SELECT_BoardGraphQLDTO +
                    "FROM Board b " +
                    "WHERE b.id < 81 " +
                    "AND b.id > 74 " +
                    "ORDER BY b.id" ,BoardGraphQLDTO.class
        ).setMaxResults(limit).getResultList();
    }

    // Upload or Update poster image
    public void updateBoardImg(Board board, String img) {
        board.setPosterImg(img);
        singlePersist(board, entityManager);
    }

    // Create Temp Board
    public long RegisterTempBoard(User user) {
        Board board = new Board();

        board.setUserId(user);
        board.setInfoCountry("TEMP");

        singlePersist(board, entityManager);

        return board.getId();
    }

    // Put Complete Board
    // TODO 객체 순회로 변경
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

        singlePersist(board, entityManager);

        return board.getId();
    }

    public long PutBoard(Board board, PutBoardDTO dto) {
        if(dto.getTitle()!=null)
            board.setTitle(dto.getTitle());
        if(dto.getInfoCountry()!=null)
            board.setInfoCountry(dto.getInfoCountry());
        if(dto.getInfoCreatedYear()!=null)
            board.setInfoCreatedYear(dto.getInfoCreatedYear());
        if(dto.getInfoTime()!=0)
            board.setInfoTime(dto.getInfoTime());
        if(dto.getInfoStory()!=null)
            board.setInfoStory(dto.getInfoStory());
        if(dto.getProducer()!=null)
            board.setProducer(dto.getProducer());
        if(dto.getDistributor()!=null)
            board.setDistributor(dto.getDistributor());
        if(dto.getPosterImg()!=null)
            board.setPosterImg(dto.getPosterImg());
        if(dto.getViewLink()!=null)
            board.setViewLink(dto.getViewLink());
        if(dto.getInfoLimit()!=0)
            board.setInfoLimit(dto.getInfoLimit());
        if(dto.getInfoSubtitle()!=null)
            board.setInfoSubtitle(dto.getInfoSubtitle());
        if(dto.getInfoCreatedDate()!=null)
            board.setInfoCreatedDate(dto.getInfoCreatedDate());

        singlePersist(board, entityManager);

        return board.getId();
    }

    public void ApproveBoard(Board board, boolean isApprove) {
        board.setApprove(isApprove);
        board.setCreatedAt(new Date());

        singlePersist(board, entityManager);
    }

    public void DeleteBoard(Board board) {
        singleRemove(board, entityManager);
    }

    public void DeleteBoards(List<Board> boards) {
        listRemove(boards, entityManager);
    }
}
