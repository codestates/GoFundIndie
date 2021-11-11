package com.IndieAn.GoFundIndie.Resolvers.Mutations;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.BoardLikeRepository;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.GqlResponseCodeDTO;
import com.IndieAn.GoFundIndie.Service.GqlUserValidService;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardLikeMutation {
    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;

    private final GqlUserValidService gqlUserValidService;
    public GqlResponseCodeDTO SwitchLikeBoard(long id, DataFetchingEnvironment env) {
        try {
            int code = gqlUserValidService.envValidCheck(env);

            if (code == 0) {
                Board board = boardRepository.findBoardId(id);
                if(board == null)
                    return GqlResponseCodeDTO.bad(4401);

                User user = gqlUserValidService.findUser(env);
                if(user == null)
                    return GqlResponseCodeDTO.bad(4400);

                boardLikeRepository.LikeBoardSwitch(user, board);
                return GqlResponseCodeDTO.ok();
            } else {
                // Token Invalid
                return GqlResponseCodeDTO.bad(code);
            }
        } catch (NullPointerException e) {
            return GqlResponseCodeDTO.bad(4000);
        }

        // Test Code
//        boardLikeRepository.LikeBoardSwitch(
//                userRepository.FindUserByIdDB(1L),
//                boardRepository.findBoardId(36L)
//        );
//        return OnlyCodeDTO.builder().code(2000).build();
    }
}
