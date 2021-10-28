package com.IndieAn.GoFundIndie.Domain.Entity;

import javax.persistence.*;
import java.util.Date;

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

    @Column(nullable = false)
    private String title;

    @Column
    private String producer;

    @Column
    private String distributor;

    @Column(name = "poster_img")
    private String posterImg;

    @Column(name = "view_link")
    private String viewLink;

    @Column(name = "info_country", nullable = false)
    private String infoCountry;

    @Column(name = "info_created_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date infoCreatedAt;

    //    running time
    @Column(name = "info_time", nullable = false)
    private int infoTime;

    //    연령제한
    @Column(name = "info_limit", nullable = false, columnDefinition = "TINYINT")
    private int infoLimit;

    @Column(name = "info_story", nullable = false, length = 1000)
    private String infoStory;

    @Column(name = "info_subtitle", nullable = false)
    private boolean infoSubtitle;

    public Board() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getInfoCreatedAt() {
        return infoCreatedAt;
    }

    public void setInfoCreatedAt(Date infoCreatedAt) {
        this.infoCreatedAt = infoCreatedAt;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
