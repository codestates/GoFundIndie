package com.IndieAn.GoFundIndie.Resolvers.DTO.Comment;

import com.IndieAn.GoFundIndie.Domain.Entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentGraphQLDTO {
    private long id;
    private int rating;
    private String userNickname;

    public CommentGraphQLDTO() {}

    public static CommentGraphQLDTO from(Comment en) {
        return CommentGraphQLDTO.builder()
                .id(en.getId())
                .rating(en.getRating())
                .userNickname(en.getUserId().getNickname())
                .build();
    }
}
