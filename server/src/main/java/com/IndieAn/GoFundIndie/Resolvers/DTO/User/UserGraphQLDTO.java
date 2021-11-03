package com.IndieAn.GoFundIndie.Resolvers.DTO.User;

import com.IndieAn.GoFundIndie.Domain.Entity.User;
import lombok.*;

@Getter
@Setter
public class UserGraphQLDTO {
    private long id;
    private boolean adminRole; //default exist
    private boolean banned; //default exist
    private String email; //non-null
    private String password; //non-null
    private String profilePicture;
    private String nickname; //non-null
    private String createdAt; // default exist
    private int totalDonation; // default exist
    private boolean adAgree; // default exist

    public UserGraphQLDTO() {}

    public static UserGraphQLDTO from(User user) {
        UserGraphQLDTO dt = new UserGraphQLDTO();
        dt.setId(user.getId());
        dt.setAdminRole(user.isAdminRole());
        dt.setBanned(user.isBanned());
        dt.setEmail(user.getEmail());
        dt.setPassword(user.getPassword());
        dt.setProfilePicture(user.getProfilePicture());
        dt.setNickname(user.getNickname());
        dt.setCreatedAt(user.getCreatedAt().toString());
        dt.setTotalDonation(user.getTotalDonation());
        dt.setAdAgree(user.isAdAgree());
        return dt;
    }
}
