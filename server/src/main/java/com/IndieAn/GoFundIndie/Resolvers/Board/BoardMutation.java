package com.IndieAn.GoFundIndie.Resolvers.Board;

import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardMutation implements GraphQLMutationResolver {
    private BoardRepository boardRepository;

    public int CreateTempBoard(Long id) {
        return 2000;
    }
}
