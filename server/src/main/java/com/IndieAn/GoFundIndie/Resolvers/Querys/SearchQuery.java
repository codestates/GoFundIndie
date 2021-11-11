package com.IndieAn.GoFundIndie.Resolvers.Querys;

import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SearchQuery {
    private final BoardRepository boardRepository;

    // 초성검색
    // 제목에 스트링 포함 여부

    public String SearchBoardName(String str) {

        char[] charArray = str.toCharArray();

        // 총 19 EA
        char[] consonant = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ".toCharArray();
        char[] lastWords = "깋낗닣딯띻맇밓빟삫싷앃잏짛찧칳킿팋핗힣".toCharArray();

        char targetEndChar = str.charAt(str.length() - 1);
        // System.out.println((int) targetChar);;

        // 마지막 ㄱ~ㅎ 인 경우
        if(targetEndChar <= 12622) {
            for(int i = 0; i < consonant.length; i++) {
                if(targetEndChar == consonant[i]) {
                    targetEndChar = lastWords[i + 1];
                }
            }
        } else {
            // 마지막 가~힣 인 경우
            for (int i = 0; i < lastWords.length; i++) {
                if (targetEndChar >= lastWords[i] && targetEndChar < lastWords[i + 1]) {
                    targetEndChar = lastWords[i + 1];
                }
            }
        }

        log.info("---- End Word ----");
        log.info(String.valueOf(charArray[0]));
        log.info(String.valueOf(targetEndChar));

        return str;
    }
}
