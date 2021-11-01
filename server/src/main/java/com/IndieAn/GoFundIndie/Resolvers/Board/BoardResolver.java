package com.IndieAn.GoFundIndie.Resolvers.Board;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardResolver implements GraphQLQueryResolver {
    private BoardRepository boardRepository;

    public String FindBoardId(Long id) {
        System.out.println(id);
        System.out.println(id);
        System.out.println(id);
        System.out.println(id);
        System.out.println(id.getClass().getName());
        Board board = boardRepository.findBoardId(1L);
        if(board != null){
            System.out.println("돼냐?");
            System.out.println("돼냐?");
            System.out.println("돼냐?");
            System.out.println("돼냐?");
            System.out.println("돼냐?");
        }
        return "아니 왜안돼";
    }
}
