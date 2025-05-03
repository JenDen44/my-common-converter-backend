package com.bulish.melnikov.converter.model;

import com.bulish.melnikov.converter.enums.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class StatusMessageDTO implements Serializable {

    private String id;

    private State state;
}
