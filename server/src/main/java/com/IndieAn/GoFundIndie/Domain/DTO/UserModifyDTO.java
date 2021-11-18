package com.IndieAn.GoFundIndie.Domain.DTO;

import lombok.Builder;

public class UserModifyDTO {
    private String nickname;
    private String password;
    private String profilePic;
    private boolean adAgree;

    public UserModifyDTO() {}

    @Builder
    public UserModifyDTO(String nickname, String password, String profilePic, boolean adAgree) {
        this.nickname = nickname;
        this.password = password;
        this.profilePic = profilePic;
        this.adAgree = adAgree;
    }

    public boolean isAdAgree() {
        return adAgree;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getProfilePic() {
        return profilePic;
    }
}
