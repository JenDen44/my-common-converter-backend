package com.bulish.melnikov.converter.enums;

public enum FileType {
    AUDIO("convert-audio-result-out-O"),
    VIDEO("convert-video-result-out-O"),
    FILE("convert-file-result-out-O");

    private final String binding;

    FileType(String binding) {
        this.binding = binding;
    }

    public String getBinding() {
        return binding;
    }
}
