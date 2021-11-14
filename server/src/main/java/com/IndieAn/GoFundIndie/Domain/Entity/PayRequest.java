package com.IndieAn.GoFundIndie.Domain.Entity;

import javax.persistence.*;

@Entity
public class PayRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String tid;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private String nextRedirectPcUrl;

    public String getNextRedirectPcUrl() {
        return nextRedirectPcUrl;
    }

    public void setNextRedirectPcUrl(String nextRedirectPcUrl) {
        this.nextRedirectPcUrl = nextRedirectPcUrl;
    }

    public PayRequest() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
