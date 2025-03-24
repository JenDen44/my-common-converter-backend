package com.bulish.melnikov.converter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Component
public class ExtensionDto {

    private String formatFrom;

    private List<String> formatsTo;

    private Integer sizeLimit;

    public ExtensionDto() {
    }
}
