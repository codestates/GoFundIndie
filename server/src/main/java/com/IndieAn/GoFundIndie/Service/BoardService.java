package com.IndieAn.GoFundIndie.Service;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public boolean BoardApproveCheck(long id) {
        return boardRepository.findBoardId(id).isApprove();
    }

    public Board FindBoardId (long id) {
        return boardRepository.findBoardId(id);
    }
}
