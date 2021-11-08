package com.IndieAn.GoFundIndie.Common;

public enum SearchTypes {
    //   - Genre = 장르별 영화
    SEARCH_TYPES_DRAMA("Drama"),
    SEARCH_TYPES_HORROR("Horror"),
    SEARCH_TYPES_ROMANCE("Romance"),
    SEARCH_TYPES_FANTASY("Fantasy"),
    SEARCH_TYPES_THRILLER("Thriller"),
    SEARCH_TYPES_DOCU("Documentary"),
    SEARCH_TYPES_FAMILY("Family"),
    SEARCH_TYPES_CRIME("Crime"),
    SEARCH_TYPES_COMEDY("Comedy"),
    SEARCH_TYPES_ANI("Animation"),
    SEARCH_TYPES_ACTION("Action"),
    SEARCH_TYPES_SF("SF"),
    SEARCH_TYPES_MUSICAL("Musical"),
    //   - My = 내가 찜한 영화
    SEARCH_TYPES_MY("My"),
    //   - My_Donation = 내가 후원한 영화
    SEARCH_TYPES_MY_DONATION("My_Donation"),
    //   - Approve_false = 미승인 보드
    SEARCH_TYPES_APPROVE_FALSE("Approve_false"),
    //   - Approve_true = 승인 보드
    SEARCH_TYPES_APPROVE_TRUE("Approve_true"),
    //   - All = isApprove 구분없이 전부
    SEARCH_TYPES_ALL("All"),
    //   - New = 최근 등록한 영화
    SEARCH_TYPES_NEW("New");

    private final String types;

    SearchTypes(String types) {
        this.types = types;
    }

    public static SearchTypes findSearchType(String type) {
        if(type.equals("")) return SEARCH_TYPES_APPROVE_TRUE;
        for(SearchTypes el : SearchTypes.values()) {
            if(el.types.equals(type)) return el;
        }
        throw new RuntimeException();
    }
}