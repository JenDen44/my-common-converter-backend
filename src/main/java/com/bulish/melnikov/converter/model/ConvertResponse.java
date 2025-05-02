package com.bulish.melnikov.converter.model;

import com.bulish.melnikov.converter.enums.State;
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
