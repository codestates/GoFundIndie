package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.CastingGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Comment.CommentGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Still.StillGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.User.UserGraphQLDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
public class AdminViewBoardDTO {
    private long id;
    private UserGraphQLDTO user;
    private boolean isApprove;
    private String title;
    private String producer;
    private String distributor;
    private String posterImg;
    private String viewLink;
    private String infoCountry;
    private String infoCreatedYear;
    private String infoCreatedDate;
    private int infoTime;
    private int infoLimit;
    private String infoStory;
    private boolean infoSubtitle;
    private String createdAt;
    private int commentAmount;
    private int likeAmount;
    private List<GenreGraphQLDTO> genre;
    private List<CastingGraphQLDTO> casting;
    private List<StillGraphQLDTO> still;
    private List<CommentGraphQLDTO> comment;

    public static AdminViewBoardDTO from(Board en) {
        return AdminViewBoardDTO.builder()
                .id(en.getId())
                .user(UserGraphQLDTO.from(en.getUserId()))
                .isApprove(en.isApprove())
                .title(en.getTitle())
                .producer(en.getProducer())
                .distributor(en.getDistributor())
                .posterImg(en.getPosterImg())
                .viewLink(en.getViewLink())
                .infoCountry(en.getInfoCountry())
                .infoCreatedYear(en.getInfoCreatedYear())
                .infoCreatedDate(en.getInfoCreatedDate())
                .infoTime(en.getInfoTime())
                .infoLimit(en.getInfoLimit())
                .infoStory(en.getInfoStory())
                .infoSubtitle(en.isInfoSubtitle())
                .createdAt(en.getCreatedAt().toString())
                .commentAmount(en.getCommentAmount())
                .likeAmount(en.getLikeAmount())
                .build();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserGraphQLDTO getUser() {
        return user;
    }

    public void setUser(UserGraphQLDTO user) {
        this.user = user;
    }

    public boolean isApprove() {
        return isApprove;
    }

    public void setApprove(boolean approve) {
        isApprove = approve;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getPosterImg() {
        return posterImg;
    }

    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg;
    }

    public String getViewLink() {
        return viewLink;
    }

    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }

    public String getInfoCountry() {
        return infoCountry;
    }

    public void setInfoCountry(String infoCountry) {
        this.infoCountry = infoCountry;
    }

    public String getInfoCreatedYear() {
        return infoCreatedYear;
    }

    public void setInfoCreatedYear(String infoCreatedYear) {
        this.infoCreatedYear = infoCreatedYear;
    }

    public String getInfoCreatedDate() {
        return infoCreatedDate;
    }

    public void setInfoCreatedDate(String infoCreatedDate) {
        this.infoCreatedDate = infoCreatedDate;
    }

    public int getInfoTime() {
        return infoTime;
    }

    public void setInfoTime(int infoTime) {
        this.infoTime = infoTime;
    }

    public int getInfoLimit() {
        return infoLimit;
    }

    public void setInfoLimit(int infoLimit) {
        this.infoLimit = infoLimit;
    }

    public String getInfoStory() {
        return infoStory;
    }

    public void setInfoStory(String infoStory) {
        this.infoStory = infoStory;
    }

    public boolean isInfoSubtitle() {
        return infoSubtitle;
    }

    public void setInfoSubtitle(boolean infoSubtitle) {
        this.infoSubtitle = infoSubtitle;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getCommentAmount() {
        return commentAmount;
    }

    public void setCommentAmount(int commentAmount) {
        this.commentAmount = commentAmount;
    }

    public int getLikeAmount() {
        return likeAmount;
    }

    public void setLikeAmount(int likeAmount) {
        this.likeAmount = likeAmount;
    }

    public List<GenreGraphQLDTO> getGenre() {
        return genre;
    }

    public void setGenre(List<GenreGraphQLDTO> genre) {
        this.genre = genre;
    }

    public List<CastingGraphQLDTO> getCasting() {
        return casting;
    }

    public void setCasting(List<CastingGraphQLDTO> casting) {
        this.casting = casting;
    }

    public List<StillGraphQLDTO> getStill() {
        return still;
    }

    public void setStill(List<StillGraphQLDTO> still) {
        this.still = still;
    }

    public List<CommentGraphQLDTO> getComment() {
        return comment;
    }

    public void setComment(List<CommentGraphQLDTO> comment) {
        this.comment = comment;
    }
}
