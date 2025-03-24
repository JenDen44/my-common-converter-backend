package com.bulish.melnikov.converter.fabric;

import com.bulish.melnikov.converter.convert.Converter;

import java.util.HashMap;
import java.util.Map;

public abstract class Fabric {

    private String format;

    protected final Map<String, Converter> converters = new HashMap<>();

    public Fabric(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public Converter getConverter(String toFormat) {
        return converters.get(toFormat);
    }
}
