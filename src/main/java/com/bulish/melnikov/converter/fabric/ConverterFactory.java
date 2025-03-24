package com.bulish.melnikov.converter.fabric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConverterFactory {
    private final Map<String, Fabric> fabrics = new HashMap<>();

    @Autowired
    public ConverterFactory(List<Fabric> fabrics) {
        for (Fabric fabric : fabrics) {
            this.fabrics.put(fabric.getFormat(), fabric);
        }
    }

    public Fabric getFactory(String fromFormat) {
        return fabrics.get(fromFormat);
    }
}
