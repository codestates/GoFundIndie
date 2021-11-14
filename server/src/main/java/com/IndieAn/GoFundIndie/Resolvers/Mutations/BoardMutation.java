package com.IndieAn.GoFundIndie.Resolvers.Mutations;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.BoardLikeRepository;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.*;
import com.IndieAn.GoFundIndie.Resolvers.DTO.GqlResponseCodeDTO;
import com.IndieAn.GoFundIndie.Service.BoardService;
import com.IndieAn.GoFundIndie.Service.GqlUserValidService;
import com.IndieAn.GoFundIndie.Service.UserService;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardMutation {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardLikeRepository boardLikeRepository;

    private final BoardService boardService;
    private final UserService userService;
    private final GqlUserValidService gqlUserValidService;

    public WrappingCreateTempBoardDTO CreateTempBoard(DataFetchingEnvironment env) {
        try {
            int code = gqlUserValidService.envValidCheck(env);

            if(code == 0) {
                User user = gqlUserValidService.findUser(env);
                if(user == null) return WrappingCreateTempBoardDTO.builder().code(4400).build();

                return WrappingCreateTempBoardDTO.builder().code(2000)
                        .data(CreateTempBoardDTO.builder()
                                .id(boardRepository.RegisterTempBoard(user))
                                .build())
                        .build();
            } else
                return WrappingCreateTempBoardDTO.builder().code(code).build();
        } catch (NullPointerException e) {
            return WrappingCreateTempBoardDTO.builder().code(4000).build();
        }
    }

    public WrappingCreateTempBoardDTO CompleteBoard(CreateBoardCompleteDTO dto, DataFetchingEnvironment env) {
        try {
            int code = gqlUserValidService.envValidCheck(env);

            if(code == 0) {
                Board board = boardRepository.findBoardId(dto.getBoardId());
                // Can not find board : 4401
                if(board == null)
                    return WrappingCreateTempBoardDTO.builder().code(4401).build();
                else if(board.getCastings().stream().noneMatch(el -> el.getPosition() == 1))
                    // Can not find Director : 4403
                    return WrappingCreateTempBoardDTO.builder().code(4403).build();
                else if(board.getBoardGenres().size() == 0)
                    // Can not find Genre : 4404
                    return WrappingCreateTempBoardDTO.builder().code(4404).build();

                User user = gqlUserValidService.findUser(env);
                // Can not find User : 4400
                if(user == null) return WrappingCreateTempBoardDTO.builder().code(4400).build();
                // Invalid User : 4301
                else if(!user.isAdminRole() && user.getId() != board.getUserId().getId())
                    return WrappingCreateTempBoardDTO.builder().code(4301).build();

                try {
                    // User != Admin : 4300
                    if(board.isApprove() && !user.isAdminRole()) {
                        return WrappingCreateTempBoardDTO.builder()
                                .code(4300)
                                .build();
                    } else {
                        try {
                            return WrappingCreateTempBoardDTO.builder()
                                    .code(2000)
                                    .data(CreateTempBoardDTO.builder()
                                            .id(boardRepository.CompleteBoard(board, dto)).build())
                                    .build();
                        } catch (IllegalArgumentException e) {
                            return WrappingCreateTempBoardDTO.builder()
                                    .code(4006).build();
                        }
                    }
                } catch (NullPointerException e) {
                    // Essential value is null : 4006
                    return WrappingCreateTempBoardDTO.builder()
                            .code(4006).build();
                }
            } else {
                // Token Invalid
                return WrappingCreateTempBoardDTO.builder().code(code).build();
            }
        } catch (NullPointerException e) {
            return WrappingCreateTempBoardDTO.builder().code(4000).build();
        }
    }

    // ! Only -- Admin --
    public WrappingCreateTempBoardDTO PutBoard(PutBoardDTO dto, DataFetchingEnvironment env) {
        try {
            int code = gqlUserValidService.envValidCheck(env);

            if (code == 0) {
                Board board = boardRepository.findBoardId(dto.getBoardId());
                // Can not find board : 4401
                if (board == null)
                    return WrappingCreateTempBoardDTO.builder().code(4401).build();

                User user = gqlUserValidService.findUser(env);
                // Can not find user : 4400
                if(user == null) return WrappingCreateTempBoardDTO.builder().code(4400).build();
                // User != Admin : 4300
                else if (!user.isAdminRole()) {
                    return WrappingCreateTempBoardDTO.builder()
                            .code(4300)
                            .build();
                } else {
                    return WrappingCreateTempBoardDTO.builder()
                            .code(2000)
                            .data(CreateTempBoardDTO.builder()
                                    .id(boardRepository.PutBoard(board, dto)).build())
                            .build();
                }
            } else {
                // Token Invalid
                return WrappingCreateTempBoardDTO.builder().code(code).build();
            }
        } catch (NullPointerException e) {
            return WrappingCreateTempBoardDTO.builder().code(4000).build();
        }
    }

    public GqlResponseCodeDTO DeleteBoard(long id, DataFetchingEnvironment env) {
        try {
            int code = gqlUserValidService.envValidCheck(env);

            if (code == 0) {
                Board board = boardRepository.findBoardId(id);
                if(board == null)
                    return GqlResponseCodeDTO.bad(4401);

                User user = gqlUserValidService.findUser(env);
                if(user == null) return GqlResponseCodeDTO.bad(4400);
                else if(!user.isAdminRole())
                    return GqlResponseCodeDTO.bad(4300);

                boardRepository.DeleteBoard(board);
                return GqlResponseCodeDTO.ok();
            } else {
                // Token Invalid
                return GqlResponseCodeDTO.bad(code);
            }
        } catch (NullPointerException e) {
            return GqlResponseCodeDTO.bad(4000);
        }
    }

    public GqlResponseCodeDTO ApproveBoard(long id, boolean isApprove, DataFetchingEnvironment env) {
        try {
            int code = gqlUserValidService.envValidCheck(env);

            if (code == 0) {
                Board board = boardRepository.findBoardId(id);
                if(board == null)
                    return GqlResponseCodeDTO.bad(4401);

                User user = gqlUserValidService.findUser(env);
                if(user == null) return GqlResponseCodeDTO.bad(4400);
                else if(!user.isAdminRole())
                    return GqlResponseCodeDTO.bad(4300);

                boardRepository.ApproveBoard(board, isApprove);
                return GqlResponseCodeDTO.ok();
            } else {
                // Token Invalid
                return GqlResponseCodeDTO.bad(code);
            }
        } catch (NullPointerException e) {
            return GqlResponseCodeDTO.bad(4000);
        }
    }
}
