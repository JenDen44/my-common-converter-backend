package com.bulish.melnikov.converter.model;

import com.bulish.melnikov.converter.enums.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ConvertRequestMsgDTO implements Serializable {

    private String id;

    private byte[] file;

    private String formatTo;

    private String formatFrom;

    private FileType type;
}