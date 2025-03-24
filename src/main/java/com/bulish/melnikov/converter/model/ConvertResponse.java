package com.bulish.melnikov.converter.model;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ConvertResponse {

    private String id;

    private State state;

    public ConvertResponse(String id, State state) {
        this.id = id;
        this.state = state;
    }
}
