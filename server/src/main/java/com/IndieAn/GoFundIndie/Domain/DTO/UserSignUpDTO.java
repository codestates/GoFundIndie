package com.IndieAn.GoFundIndie.Domain.DTO;

public class UserSignUpDTO {
    private String email;
    private String password;
    private String nickname;
    private String profilePic;
    private boolean adAgree;

    public boolean isAdAgree() {
        return adAgree;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setAdAgree(boolean adAgree) {
        this.adAgree = adAgree;
    }
}
