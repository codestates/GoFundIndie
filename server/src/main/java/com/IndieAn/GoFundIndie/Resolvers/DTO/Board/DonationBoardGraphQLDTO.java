package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Slf4j
public class DonationBoardGraphQLDTO {

    @NotBlank
    private long id;

    private boolean isApprove;
    private String title;
    private String posterImg;
    private String infoCreatedYear;
    private String infoCreatedDate;
    private Integer donationAmount; //    Donation amount
    private String donationCreatedAt; //    Donation created_at

    public DonationBoardGraphQLDTO() {}

    @Builder
    public DonationBoardGraphQLDTO(long id, boolean isApprove, String title,
                                   String posterImg, String infoCreatedYear,
                                   String infoCreatedDate, Integer donationAmount,
                                   Date donationCreatedAt) {
        Assert.notNull(donationAmount, "donationAmount is null");
        Assert.notNull(donationCreatedAt, "donationCreatedAt is null");

        this.id = id;
        this.isApprove = isApprove;
        this.title = title;
        this.posterImg = posterImg;
        this.infoCreatedYear = infoCreatedYear;
        this.infoCreatedDate = infoCreatedDate;
        this.donationAmount = donationAmount;
        this.donationCreatedAt = donationCreatedAt.toString();
    }

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

    public String getPosterImg() {
        return posterImg;
    }

    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg;
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

    public Integer getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(Integer donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String getDonationCreatedAt() {
        return donationCreatedAt;
    }

    public void setDonationCreatedAt(String donationCreatedAt) {
        this.donationCreatedAt = donationCreatedAt;
    }
}
