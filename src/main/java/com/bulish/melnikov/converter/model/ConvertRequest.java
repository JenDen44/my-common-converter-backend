package com.bulish.melnikov.converter.model;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ConvertRequest {

    private MultipartFile file;

    private String formatTo;

    private String formatFrom;
}
