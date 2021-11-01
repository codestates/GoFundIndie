package com.IndieAn.GoFundIndie.Resolvers.Board;

import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardMutation implements GraphQLMutationResolver {
    private BoardRepository boardRepository;
    private UserRepository userRepository;

    private final HashMap<String, Object> data = new HashMap<>();

    // Create Temp Board
    public TempBoardDTO CreateTempBoard(Long id) {
        System.out.println("-----------start----------");
        User user = userRepository.FindUserByIdDB(id);
        long boardId = boardRepository.RegisterTempBoard(user);
        System.out.println("-----------create suc----------");
        return TempBoardDTO.builder().build();
//        try {
//
//        } catch (NullPointerException e) {
//            return TempBoardDTO.builder()
//                    .code(4000).build();
//        }
    }
}
