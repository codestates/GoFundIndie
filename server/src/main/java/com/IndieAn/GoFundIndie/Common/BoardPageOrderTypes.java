package com.IndieAn.GoFundIndie.Common;

public enum BoardPageOrderTypes {
    // 인기순
    BOARD_PAGE_ORDER_TYPES_POPULAR("Popular"),
    // 가나다순 ( 사전순 )
    BOARD_PAGE_ORDER_TYPES_DIC("Dictionary"),
    // 최신순
    BOARD_PAGE_ORDER_TYPES_NEW("New");

    private final String types;

    BoardPageOrderTypes(String types) {
        this.types = types;
    }

    public static BoardPageOrderTypes findPathType(String path) {
        for(BoardPageOrderTypes el : BoardPageOrderTypes.values()) {
            if(el.types.equals(path)) return el;
        }
        throw new RuntimeException();
    }
}
