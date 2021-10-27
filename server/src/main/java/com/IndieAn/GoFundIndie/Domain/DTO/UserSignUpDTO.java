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
}
