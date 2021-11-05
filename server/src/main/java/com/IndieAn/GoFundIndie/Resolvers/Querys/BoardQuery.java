package com.IndieAn.GoFundIndie.Resolvers.Querys;

import com.IndieAn.GoFundIndie.Common.SearchTypes;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.*;
import com.IndieAn.GoFundIndie.Service.UserService;
import graphql.kickstart.servlet.context.GraphQLServletContext;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardQuery {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    //TODO : Sort comment by like & limit 5
    public WrappingViewBoardDTO FindBoardId(Long id) {
        try {
            return WrappingViewBoardDTO.builder()
                    .code(2000)
                    .data(ViewBoardDTO.from(boardRepository.findBoardId(id)))
                    .build();
        } catch (NullPointerException e) {
            return WrappingViewBoardDTO.builder()
                    .code(4401)
                    .build();
        }
    }

    public WrappingAdminViewBoardDTO FindBoardIdAdmin(Long id, DataFetchingEnvironment env) {
        try {
            GraphQLServletContext context = env.getContext();
            HttpServletRequest request = context.getHttpServletRequest();
            String accessToken = request.getHeader("accesstoken");

            //   - No accessToken in the Header :
            if(accessToken == null)
                return WrappingAdminViewBoardDTO.builder().code(4000).build();

            Map<String, Object> checkToken = userService.CheckToken(accessToken);

            if(checkToken.get("email") == null) {
                return WrappingAdminViewBoardDTO.builder()
                        .code(Integer.parseInt(checkToken.get("code").toString())).build();
            } else if(!userService.FindUserUseEmail(checkToken.get("email").toString()).isAdminRole()) {
                return WrappingAdminViewBoardDTO.builder().code(4300).build();
            } else {
                return WrappingAdminViewBoardDTO.builder()
                        .code(2000)
                        .data(AdminViewBoardDTO.from(boardRepository.findBoardId(id)))
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
                        GraphQLServletContext context = env.getContext();
                        HttpServletRequest request = context.getHttpServletRequest();
                        String accessToken = request.getHeader("accesstoken");

                        //   - No accessToken in the Header :
                        if(accessToken == null)
                            return WrappingBoardGraphQLsDTO.builder().code(4000).build();

                        Map<String, Object> checkToken = userService.CheckToken(accessToken);

                        //   - Token invalid case :
                        if(checkToken.get("email") == null) {
                            return WrappingBoardGraphQLsDTO.builder()
                                    .code(Integer.parseInt(checkToken.get("code").toString())).build();
                        } else {
                            return WrappingBoardGraphQLsDTO.builder()
                                    .code(2000)
                                    .data(boardRepository.findBoardsByLike(
                                            userService.FindUserUseEmail(
                                                    checkToken.get("email").toString()), limit))
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
}
