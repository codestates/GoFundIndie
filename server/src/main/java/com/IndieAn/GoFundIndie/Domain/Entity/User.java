package com.IndieAn.GoFundIndie.Domain.Entity;

<<<<<<< HEAD
import com.IndieAn.GoFundIndie.Resolvers.User.UserGraphQLDTO;

=======
>>>>>>> 4b52c78 (rollback)
import javax.persistence.*;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "admin_role", columnDefinition = "boolean default false")
    private boolean adminRole;

    @Column(columnDefinition = "boolean default false")
    private boolean banned;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(nullable = false)
    private String nickname;

<<<<<<< HEAD
    @Column(name = "created_at", columnDefinition = "datetime default now()")
=======
    @Column(name = "created_at")
>>>>>>> 4b52c78 (rollback)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "total_donation", columnDefinition = "integer default 0")
    private int totalDonation;

    @Column(name = "ad_agree", columnDefinition = "boolean default true")
    private boolean adAgree;

    public User() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAdminRole() {
        return adminRole;
    }

    public void setAdminRole(boolean adminRole) {
        this.adminRole = adminRole;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getTotalDonation() {
        return totalDonation;
    }

    public void setTotalDonation(int totalDonation) {
        this.totalDonation = totalDonation;
    }

    public boolean isAdAgree() {
        return adAgree;
    }

    public void setAdAgree(boolean adAgree) {
        this.adAgree = adAgree;
    }
<<<<<<< HEAD

    public static User from(UserGraphQLDTO dto) {
        User user = new User();

        if(dto.getEmail() == null || dto.getPassword() == null || dto.getNickname() == null) {
            return null;
        } else {
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword());
            user.setNickname(dto.getNickname());
        }
        if(dto.isAdminRole()) user.setAdminRole(dto.isAdminRole());
        if(dto.isBanned()) user.setBanned(dto.isBanned());
        if(dto.getProfilePicture() != null) user.setProfilePicture(dto.getProfilePicture());
        if(dto.getTotalDonation() != 0) user.setTotalDonation(dto.getTotalDonation());
        if(dto.isAdAgree()) user.setAdAgree(dto.isAdAgree());
        user.setCreatedAt(new Date());

        return user;
    }
=======
>>>>>>> 4b52c78 (rollback)
}
