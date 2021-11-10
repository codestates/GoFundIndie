package com.IndieAn.GoFundIndie.Common;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public enum SearchTypes {
    //   - Genre = 장르별 영화
    SEARCH_TYPES_DRAMA("독립영화는 죄다 드라마던데 확인해보세요"),
    SEARCH_TYPES_HORROR("🎃 추워지기 전에 공포영화 👻"),
    SEARCH_TYPES_ROMANCE("달달한 로맨스 영화 추천!"),
    SEARCH_TYPES_FANTASY("#판타지를 #제작사없이 #어떻게 #찍지"),
    SEARCH_TYPES_THRILLER("짜릿한 스릴러는 어떠신가요?"),
    SEARCH_TYPES_DOCU("#DOCUMENTARY #옆집 #이야기일수도"),
    SEARCH_TYPES_FAMILY("연말에는 가족과 함께하세요"),
    SEARCH_TYPES_CRIME("#신선한 #범죄 #독립영화"),
    SEARCH_TYPES_COMEDY("오늘 하루를 즐거운 코미디로 마무리해보세요"),
    SEARCH_TYPES_ANI("독창적인 애니메이션을 만나보세요"),
    SEARCH_TYPES_ACTION("액션 영화가 있을리가 했는데"),
    SEARCH_TYPES_SF("#공상과학 #창의력 #SF #👽"),
    SEARCH_TYPES_MUSICAL("뮤지컬 영화"),
    //   - Random = 완전 랜덤
    SEARCH_TYPES_RANDOM("이런 영화는 어떠세요?"),
    //   - Seoul2020 = 서울독립영화 2020 수상작
    SEARCH_TYPES_SEOUL2020("서울독립영화제 2020 수상작"),
    //   - New = 최근 등록한 영화
    SEARCH_TYPES_NEW("새로 등록된 영화를 살펴보세요"),
    //     !
    //     로그인 상태 필요
    //   - My = 내가 찜한 영화
    SEARCH_TYPES_MY("담아둔 영화 리스트 ❤️"),
    //   - My_Donation = 내가 후원한 영화
    SEARCH_TYPES_MY_DONATION("내가 후원한 독립영화들 입니다 👍"),
    //     !
    //     Admin only types : 3 EA
    //   - Approve_false = 미승인 보드
    SEARCH_TYPES_APPROVE_FALSE("Approve_false"),
    //   - Approve_true = 승인 보드
    SEARCH_TYPES_APPROVE_TRUE("Approve_true"),
    //   - All = isApprove 구분없이 전부
    SEARCH_TYPES_ALL("All");

    private final String types;

    SearchTypes(String types) {
        this.types = types;
    }

    public static String getPhrase(SearchTypes type) {
//        for(SearchTypes el : SearchTypes.values()) {
//            if(el.equals(type)) return el.types;
//        }
//        throw new RuntimeException();
        return type.types;
    }

    public static List<SearchTypes> getRandomType(int limit, boolean isLogin) {
        AtomicInteger rangeLimit = new AtomicInteger(3);
        if(!isLogin) rangeLimit.set(5);

        List<SearchTypes> searchTypesList = new java.util
                .ArrayList<>(List.of(SearchTypes.values()))
                .subList(0, SearchTypes.values().length - rangeLimit.get());

        Collections.shuffle(searchTypesList);

        try {
            return searchTypesList.subList(0,limit);
        } catch (IndexOutOfBoundsException e) {
            return searchTypesList;
        }
    }
}