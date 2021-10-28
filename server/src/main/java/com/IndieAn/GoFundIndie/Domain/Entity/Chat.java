package com.IndieAn.GoFundIndie.Domain.Entity;

import javax.persistence.*;

@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id_1", nullable = false)
    private User userId1;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id_2", nullable = false)
    private User userId2;

    public Chat() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserId1() {
        return userId1;
    }

    public void setUserId1(User userId1) {
        this.userId1 = userId1;
    }

    public User getUserId2() {
        return userId2;
    }

    public void setUserId2(User userId2) {
        this.userId2 = userId2;
    }
}
