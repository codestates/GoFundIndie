package com.IndieAn.GoFundIndie.Resolvers.Querys;

import com.IndieAn.GoFundIndie.Repository.BoardSearchRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.SearchBoardDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Board.WrappingSearchBoardDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardSearchQuery {
    private final BoardSearchRepository boardSearchRepository;

    private final char[] consonant = "ㄱㄲㄴㄷㄸㄹㅁㅂㅃㅅㅆㅇㅈㅉㅊㅋㅌㅍㅎ".toCharArray();
    private final char[] middWords = "가까나다따라마바빠사싸아자짜차카타파하".toCharArray();
    private final char[] lastWords = "깋낗닣딯띻맇밓빟삫싷앃잏짛찧칳킿팋핗힣".toCharArray();

    private List<String> consonantMatch(char ch) {
        char targetStaChar = ch;
        char targetEndChar = targetStaChar;

        if(targetEndChar <= 12622) {
            for(int i = 0; i < 19; i++) {
                if(targetEndChar == consonant[i]) {
                    targetStaChar = middWords[i];
                    targetEndChar = lastWords[i];
                    break;
                }
            }
        } else {
            for (int i = 0; i < 19; i++) {
                if (targetEndChar >= middWords[i] && targetEndChar <= lastWords[i]) {
                    targetStaChar = middWords[i];
                    targetEndChar = lastWords[i];
                    break;
                }
            }
        }

        List<String> result = new ArrayList<>();
        result.add(String.valueOf(targetStaChar));
        result.add(String.valueOf(targetEndChar));
        return result;
    }

    // TODO 검색 로직 Full Search Text 로 교체
    public WrappingSearchBoardDTO SearchBoardName(String str) {
        if(str == null || str.equals("") || str.equals(" ")) {
            return WrappingSearchBoardDTO.builder().code(2000)
                    .data(boardSearchRepository.SearchBoardsFromNull(10))
                    .build();
        } else {
            char lastWord = str.toCharArray()[str.length() - 1];

            // case: korean consonant / 12593 'ㄱ' / 12622 'ㅎ'
            if(lastWord > 12592 && lastWord < 12623) {
                List<String> params = consonantMatch(lastWord);
                String lastWordDeleted = str.substring(0, str.length() - 1);

                return WrappingSearchBoardDTO.builder().code(2000)
                        .data(boardSearchRepository.SearchBoardsConsonant(
                                lastWordDeleted + params.get(0),
                                lastWordDeleted + params.get(1),
                                10))
                        .build();
            } else {
                return WrappingSearchBoardDTO.builder().code(2000)
                        .data(boardSearchRepository.SearchBoards(str, 10))
                        .build();
            }
        }
    }
}
