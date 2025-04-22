package com.bulish.melnikov.converter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@Component
@NoArgsConstructor
public class ConvertRequest {

    private MultipartFile file;

    private String formatTo;

    private String formatFrom;
}
