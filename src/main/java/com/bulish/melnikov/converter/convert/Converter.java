package com.bulish.melnikov.converter.convert;

import lombok.Getter;

import java.io.File;

@Getter
public abstract class Converter {

    private String format;

    public Converter(String format) {
        this.format = format;
    }

    public abstract byte[] convert(File file);
}
