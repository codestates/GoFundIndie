package com.IndieAn.GoFundIndie.Domain.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User userId;

    @Column(name = "is_approve", columnDefinition = "boolean default false")
    private boolean isApprove;

    @Column
    private String title;

    @Column
    private String producer;

    @Column
    private String distributor;

    @Column(name = "poster_img")
    private String posterImg;

    @Column(name = "view_link")
    private String viewLink;

    @Column(name = "info_country")
    private String infoCountry;

    //    개봉년도
    @Column(name = "info_created_year")
    private String infoCreatedYear;

    //    개봉 월 일
    @Column(name = "info_created_date")
    private String infoCreatedDate;

    //    running time
    @Column(name = "info_time")
    private int infoTime;

    //    연령제한
    @Column(name = "info_limit", columnDefinition = "TINYINT")
    private int infoLimit;

    @Column(name = "info_story", length = 1000)
    private String infoStory;

    @Column(name = "info_subtitle", columnDefinition = "boolean default false")
    private boolean infoSubtitle;

    @Column(name = "created_at", columnDefinition = "datetime default now()")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @OneToMany(mappedBy = "boardId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BoardLike> boardLikes = new ArrayList<>();

    @OneToMany(mappedBy = "boardId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BoardReport> boardReports = new ArrayList<>();

    @OneToMany(mappedBy = "boardId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "boardId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Still> stills = new ArrayList<>();

    @OneToMany(mappedBy = "boardId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BoardGenre> boardGenres = new ArrayList<>();

    @OneToMany(mappedBy = "boardId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Casting> castings = new ArrayList<>();

    public Board() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<BoardLike> getBoardLikes() {
        return boardLikes;
    }

    public void setBoardLikes(List<BoardLike> boardLikes) {
        this.boardLikes = boardLikes;
    }

    public List<BoardReport> getBoardReports() {
        return boardReports;
    }

    public void setBoardReports(List<BoardReport> boardReports) {
        this.boardReports = boardReports;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Still> getStills() {
        return stills;
    }

    public void setStills(List<Still> stills) {
        this.stills = stills;
    }

    public List<BoardGenre> getBoardGenres() {
        return boardGenres;
    }

    public void setBoardGenres(List<BoardGenre> boardGenres) {
        this.boardGenres = boardGenres;
    }

    public List<Casting> getCastings() {
        return castings;
    }

    public void setCastings(List<Casting> castings) {
        this.castings = castings;
    }
}
