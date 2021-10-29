package com.IndieAn.GoFundIndie.Resolvers.Board;

import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardResolver implements GraphQLQueryResolver {
    private BoardRepository boardRepository;

    public BoardGraphQLDTO FindBoardId(Long id) {
        return BoardGraphQLDTO.from(boardRepository.findBoardId(id));
    }
}
