package com.IndieAn.GoFundIndie.Resolvers.Querys;

import com.IndieAn.GoFundIndie.Common.SearchTypes;
import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.*;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.*;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Comment.CommentGraphQLDTO;
import com.IndieAn.GoFundIndie.Service.GqlUserValidService;
import com.IndieAn.GoFundIndie.Service.UserService;
import graphql.kickstart.servlet.context.GraphQLServletContext;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardQuery {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CastingRepository castingRepository;
    private final GenreRepository genreRepository;
    private final ImageRepository imageRepository;
    private final BoardLikeRepository boardLikeRepository;

    private final UserService userService;
    private final GqlUserValidService gqlUserValidService;

    public WrappingViewBoardDTO FindBoardId(Long id, DataFetchingEnvironment env) {
        try {
            Board board = boardRepository.findBoardId(id);
            List<CommentGraphQLDTO> commentList = commentRepository.findCommentByBoard(id, null);

            ViewBoardDTO dto = ViewBoardDTO.from(board);
            dto.setCasting(castingRepository.findCastingByBoard(id));
            dto.setComment(commentList);
            dto.setGenre(genreRepository.findGenreByBoard(id));
            dto.setStill(imageRepository.findStillByBoard(id));
            dto.setAverageRating(3);

            int code = gqlUserValidService.envValidCheck(env);

            if(code == 0) {
                dto.setLiked(boardLikeRepository
                        .isLikedBoard(gqlUserValidService.findUser(env),board));
            } else {
                dto.setLiked(false);
            }

            return WrappingViewBoardDTO.builder()
                    .code(2000)
                    .data(dto)
                    .build();
        } catch (NullPointerException e) {
            return WrappingViewBoardDTO.builder()
                    .code(4401)
                    .build();
        }
    }

    public WrappingAdminViewBoardDTO FindBoardIdAdmin(Long id, DataFetchingEnvironment env) {
        try {
            int code = gqlUserValidService.envValidCheck(env);

            if(code != 0) {
                return WrappingAdminViewBoardDTO.builder().code(code).build();
            } else if(!gqlUserValidService.findUser(env).isAdminRole()) {
                return WrappingAdminViewBoardDTO.builder().code(4300).build();
            } else {
                AdminViewBoardDTO dto = AdminViewBoardDTO.from(boardRepository.findBoardId(id));
                dto.setCasting(castingRepository.findCastingByBoard(id));
                dto.setComment(commentRepository.findCommentByBoard(id,5));
                dto.setGenre(genreRepository.findGenreByBoard(id));
                dto.setStill(imageRepository.findStillByBoard(id));

                return WrappingAdminViewBoardDTO.builder()
                        .code(2000)
                        .data(dto)
                        .build();
            }

            // Test Code
//            return WrappingAdminViewBoardDTO.builder()
//                    .code(2000)
//                    .data(AdminViewBoardDTO.from(boardRepository.findBoardId(id)))
//                    .build();

        } catch (NullPointerException e) {
            return WrappingAdminViewBoardDTO.builder().code(4000).build();
        }
    }

    public WrappingBoardGraphQLsDTO FindBoards(SearchTypes type, int limit, DataFetchingEnvironment env) {
        if(type == null){
            return WrappingBoardGraphQLsDTO.builder()
                    .code(2000)
                    .data(boardRepository.findBoards(true, limit))
                    .build();
        }
        try {
            switch (type) {
                //   - My = 내가 찜한 영화
                case SEARCH_TYPES_MY:
                    try {
                        int code = gqlUserValidService.envValidCheck(env);

                        if(code != 0) {
                            return WrappingBoardGraphQLsDTO.builder().code(code).build();
                        } else {
                            return WrappingBoardGraphQLsDTO.builder()
                                    .code(2000)
                                    .data(boardRepository.findBoardsByMy(
                                            gqlUserValidService.findUser(env), limit))
                                    .build();
                        }

                        // Test Code : No Access Token
//                        return WrappingBoardGraphQLsDTO.builder()
//                                .code(2000)
//                                .data(boardRepository.findBoardsByLike(
//                                                userRepository.FindUserByIdDB(7L), limit))
//                                .build();

                    } catch (NullPointerException e) {
                        return WrappingBoardGraphQLsDTO.builder().code(4000).build();
                    }
                case SEARCH_TYPES_MY_DONATION:
                    try {
                        int code = gqlUserValidService.envValidCheck(env);

                        if(code != 0) {
                            return WrappingBoardGraphQLsDTO.builder().code(code).build();
                        } else {
                            return WrappingBoardGraphQLsDTO.builder()
                                    .code(2000)
                                    .data(boardRepository.findBoardsByMyDonation(
                                            gqlUserValidService.findUser(env), limit))
                                    .build();
                        }

                        // Test Code
//                        return WrappingBoardGraphQLsDTO.builder()
//                                .code(2000)
//                                .data(boardRepository.findBoardsByMyDonation(
//                                        userRepository.FindUserByIdDB(3L), limit
//                                ))
//                                .build();

                    } catch (NullPointerException e) {
                        return WrappingBoardGraphQLsDTO.builder().code(4000).build();
                    }
                //   - Approve_false = 미승인 보드
                case SEARCH_TYPES_APPROVE_FALSE:
                    return WrappingBoardGraphQLsDTO.builder()
                            .code(2000)
                            .data(boardRepository.findBoards(false, limit))
                            .build();
                //   - Approve_true = 승인된 보드
                case SEARCH_TYPES_APPROVE_TRUE:
                    return WrappingBoardGraphQLsDTO.builder()
                            .code(2000)
                            .data(boardRepository.findBoards(true, limit))
                            .build();
                //   - All = 필터 없이 모든 보드
                case SEARCH_TYPES_ALL:
                    return WrappingBoardGraphQLsDTO.builder()
                            .code(2000)
                            .data(boardRepository.findAllBoards(limit))
                            .build();
                //   - New = 최근 승인된 순으로 정렬
                case SEARCH_TYPES_NEW:
                    return WrappingBoardGraphQLsDTO.builder()
                            .code(2000)
                            .data(boardRepository.findBoardsNew(limit))
                            .build();
                //   - Genre = 장르별 영화
                default:
                    return WrappingBoardGraphQLsDTO.builder()
                            .code(2000)
                            .data(boardRepository.findBoardsByGenre(type, limit))
                            .build();
            }
        } catch (RuntimeException e) {
            //   - type invalid case :
            return WrappingBoardGraphQLsDTO.builder()
                    .code(4009)
                    .build();
        }
    }

    public WrappingRandomBoardsDTO FindRandomBoard(DataFetchingEnvironment env) {
        List<RandomBoardDTO> list = new ArrayList<>();

        // TEST HARD CORD
        list.add(RandomBoardDTO.builder()
                .phrase("드라마 장르 추천 문구")
                .data(boardRepository.findBoardsByGenre(SearchTypes.SEARCH_TYPES_DRAMA, 12))
                .build());

        list.add(RandomBoardDTO.builder()
                .phrase("#로멘스 #멜로")
                .data(boardRepository.findBoardsByGenre(SearchTypes.SEARCH_TYPES_ROMANCE, 12))
                .build());

        list.add(RandomBoardDTO.builder()
                .phrase("#DOCUMENTARY #생생함")
                .data(boardRepository.findBoardsByGenre(SearchTypes.SEARCH_TYPES_DOCU, 12))
                .build());

        list.add(RandomBoardDTO.builder()
                .phrase("하루의 마무리를 즐거운 코미디 영화와 함께")
                .data(boardRepository.findBoardsByGenre(SearchTypes.SEARCH_TYPES_COMEDY, 12))
                .build());

        list.add(RandomBoardDTO.builder()
                .phrase("동화책 같은 추천 애니메이션 영화")
                .data(boardRepository.findBoardsByGenre(SearchTypes.SEARCH_TYPES_ANI, 12))
                .build());

        return WrappingRandomBoardsDTO.builder().code(2000).data(list).build();
    }
}
