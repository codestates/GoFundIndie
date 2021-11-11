package com.IndieAn.GoFundIndie.Resolvers.Querys;

import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SearchQuery {
    private final BoardRepository boardRepository;

    // 초성검색
    // 제목에 스트링 포함 여부

    public String SearchBoardName(String str) {
        return str.getBytes(StandardCharsets.UTF_8).toString();
    }
}
