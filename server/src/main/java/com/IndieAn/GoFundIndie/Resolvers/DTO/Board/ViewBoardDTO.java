package com.IndieAn.GoFundIndie.Resolvers.DTO.Board;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.CastingGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Comment.CommentGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Genre.GenreGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Still.StillGraphQLDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class ViewBoardDTO {
    private long id;
    private long userId;
    private boolean isApprove;
    private String title;
    private String producer;
    private String distributor;
    private String posterImg;
    private String viewLink;
    private String infoCountry;
    private String infoCreatedAt;
    private int infoTime;
    private int infoLimit;
    private String infoStory;
    private boolean infoSubtitle;
    private String createdAt;
    private List<GenreGraphQLDTO> genre;
    private List<CastingGraphQLDTO> casting;
    private List<StillGraphQLDTO> still;
    private List<CommentGraphQLDTO> comment;

    public ViewBoardDTO() {}

    public static ViewBoardDTO from(Board en) {
        return ViewBoardDTO.builder()
                .id(en.getId())
                .userId(en.getUserId().getId())
                .isApprove(en.isApprove())
                .title(en.getTitle())
                .producer(en.getProducer())
                .distributor(en.getDistributor())
                .posterImg(en.getPosterImg())
                .viewLink(en.getViewLink())
                .infoCountry(en.getInfoCountry())
                .infoCreatedAt(en.getInfoCreatedAt().toString())
                .infoTime(en.getInfoTime())
                .infoLimit(en.getInfoLimit())
                .infoStory(en.getInfoStory())
                .infoSubtitle(en.isInfoSubtitle())
                .createdAt(en.getCreatedAt().toString())
                .genre(
                        en.getBoardGenres().stream()
                                .map(el -> GenreGraphQLDTO.from(el.getGenreId()))
                                .collect(Collectors.toList()))
                .casting(
                        en.getCastings().stream()
                                .map(CastingGraphQLDTO::from)
                                .collect(Collectors.toList()))
                .still(
                        en.getStills().stream()
                                .map(StillGraphQLDTO::from)
                                .collect(Collectors.toList()))
                .comment(
                        en.getComments().stream()
                                .map(CommentGraphQLDTO::from)
                                .collect(Collectors.toList()))
                .build();
    }
}
