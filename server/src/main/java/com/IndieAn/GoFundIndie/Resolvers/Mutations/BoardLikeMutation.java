package com.IndieAn.GoFundIndie.Resolvers.Mutations;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Repository.BoardLikeRepository;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.OnlyCodeDTO;
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
    public OnlyCodeDTO SwitchLikeBoard(long id, DataFetchingEnvironment env) {
        try {
            int code = gqlUserValidService.envValidCheck(env);

            if (code == 0) {
                Board board = boardRepository.findBoardId(id);
                if(board == null)
                    return OnlyCodeDTO.builder().code(4401).build();

                boardLikeRepository.LikeBoardSwitch(gqlUserValidService.findUser(env), board);
                return OnlyCodeDTO.builder().code(2000).build();
            } else {
                // Token Invalid
                return OnlyCodeDTO.builder().code(code).build();
            }
        } catch (NullPointerException e) {
            return OnlyCodeDTO.builder().code(4000).build();
        }

        // Test Code
//        boardLikeRepository.LikeBoardSwitch(
//                userRepository.FindUserByIdDB(1L),
//                boardRepository.findBoardId(36L)
//        );
//        return OnlyCodeDTO.builder().code(2000).build();
    }
}
