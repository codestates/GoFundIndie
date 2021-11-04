package com.IndieAn.GoFundIndie.Common;

public enum ImagePathTypes {
    IMAGE_PATH_TYPES_USER("user"),
    IMAGE_PATH_TYPES_STILL("still"),
    IMAGE_PATH_TYPES_CASTING("casting"),
    IMAGE_PATH_TYPES_BOARD("board");

    private final String types;

    ImagePathTypes(String path) { this.types = path; }

    public static ImagePathTypes findImagePathType(String path) {
        for(ImagePathTypes el : ImagePathTypes.values()) {
            if(el.types.equals(path)) return el;
        }
        throw new RuntimeException();
    }
}
