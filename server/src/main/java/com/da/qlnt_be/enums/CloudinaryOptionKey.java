package com.da.qlnt_be.enums;

import lombok.Getter;

@Getter
public enum CloudinaryOptionKey {
    FOLDER("folder"),
    PUBLIC_ID("public_id"),
    USE_FILENAME("use_filename"),
    UNIQUE_FILENAME("unique_filename"),
    OVERWRITE("overwrite"),
    DISPLAY_NAME("display_name"),
    RESOURCE_TYPE("resource_type"),
    WIDTH("width"),
    HEIGHT("height"),
    CROP("crop"),
    QUALITY("quality"),
    TAGS("tags"),
    CONTEXT("context"),
    INVALIDATE("invalidate"),
    UPLOAD_PRESET("upload_preset");

    private final String key;

    CloudinaryOptionKey(String key) {
        this.key = key;
    }
}