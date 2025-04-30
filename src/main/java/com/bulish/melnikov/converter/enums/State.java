package com.bulish.melnikov.converter.enums;

import lombok.Getter;

@Getter
public enum State {

    INIT("init"),
    CONVERTING("converting"),
    CONVERTED("converted"),
    IN_ERROR("in_error");

    private final String status;

    State(String status) {
        this.status = status;
    }
}
