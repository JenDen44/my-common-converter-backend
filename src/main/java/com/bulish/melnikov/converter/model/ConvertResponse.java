package com.bulish.melnikov.converter.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
@NoArgsConstructor
@AllArgsConstructor
public class ConvertResponse {

    private String id;

    private State state;
}
