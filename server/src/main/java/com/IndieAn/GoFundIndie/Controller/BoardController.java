package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final HashMap<String, Object> body = new HashMap<>();

    //    Test
    private final EntityManager em;
    private final BoardRepository boardRepository;

    @GetMapping(value = "/")
    public ResponseEntity<?> TestServer () {
        Board board = boardRepository.findBoardId(1);
        body.clear();
        body.put("server", "connected");
        body.put("board 1 title", board.getTitle());
        return ResponseEntity.status(200).body(body);
    }
}
