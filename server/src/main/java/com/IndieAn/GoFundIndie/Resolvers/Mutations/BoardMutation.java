package com.IndieAn.GoFundIndie.Resolvers.Mutations;

import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.CreateBoardCompleteDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.CreateTempBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.WrappingCreateBoardCompleteDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.WrappingCreateTempBoardDTO;
import com.IndieAn.GoFundIndie.Service.UserService;
import graphql.kickstart.servlet.context.GraphQLServletContext;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardMutation {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    private String accessToken = null;
    private Map<String, Object> checkToken = null;

    private GraphQLServletContext context = null;
    private HttpServletRequest request = null;

    public WrappingCreateTempBoardDTO CreateTempBoard(DataFetchingEnvironment env) {
        try {
            context = env.getContext();
            request = context.getHttpServletRequest();
            accessToken = request.getHeader("accesstoken");

            // No token in the Header : 4000
            if(context == null || request == null || accessToken == null)
                return WrappingCreateTempBoardDTO.builder().code(4000).build();

            checkToken = userService.CheckToken(accessToken);

            if(checkToken.get("email") != null) {
                long boardId = boardRepository.RegisterTempBoard(
                        userService.FindUserUseEmail(checkToken.get("email").toString()));
                return WrappingCreateTempBoardDTO.builder().code(2000)
                        .data(CreateTempBoardDTO.builder().id(boardId).build())
                        .build();
            } else {
                // Token Invalid
                return WrappingCreateTempBoardDTO.builder()
                        .code(Integer.parseInt(checkToken.get("code").toString())).build();
            }

            // Test Code : No Access Token
//            return WrappingCreateTempBoardDTO.builder()
//                    .code(2000)
//                    .data(CreateTempBoardDTO.builder().id(boardRepository
//                            .RegisterTempBoard(userRepository.FindUserByIdDB(1L))).build())
//                    .build();

        } catch (NullPointerException e) {
            return WrappingCreateTempBoardDTO.builder().code(4000).build();
        }
    }

    public WrappingCreateTempBoardDTO CompleteBoard(CreateBoardCompleteDTO dto, DataFetchingEnvironment env) {
        try {
            context = env.getContext();
            request = context.getHttpServletRequest();
            accessToken = request.getHeader("accesstoken");

            // No token in the Header : 4000
            if(context == null || request == null || accessToken == null)
                return WrappingCreateTempBoardDTO.builder().code(4000).build();

            checkToken = userService.CheckToken(accessToken);

            if(checkToken.get("email") != null) {
                User user = userService.FindUserUseEmail(checkToken.get("email").toString());

                // Invalid UserId
                if(!user.isAdminRole() && user.getId() != dto.getUserId())
                    return WrappingCreateTempBoardDTO.builder().code(4301).build();

                try {
                    return WrappingCreateTempBoardDTO.builder()
                            .code(2000)
                            .data(CreateTempBoardDTO.builder()
                                    .id(boardRepository.CompleteBoard(dto).getId()).build())
                            .build();
                } catch (NullPointerException e) {
                    // Essential value is null
                    return WrappingCreateTempBoardDTO.builder()
                            .code(4006).build();
                }
            } else {
                // Token Invalid
                return WrappingCreateTempBoardDTO.builder()
                        .code(Integer.parseInt(checkToken.get("code").toString()))
                        .build();
            }

            // Test Code : No Access Token
//            return WrappingCreateTempBoardDTO.builder()
//                    .code(2000)
//                    .data(CreateTempBoardDTO.builder().id(boardRepository.CompleteBoard(dto).getId()).build())
//                    .build();

        } catch (NullPointerException e) {
            return WrappingCreateTempBoardDTO.builder().code(4000).build();
        }
    }
}
