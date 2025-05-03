package com.bulish.melnikov.converter.service;

import com.bulish.melnikov.converter.convert.Converter;
import com.bulish.melnikov.converter.enums.State;
import com.bulish.melnikov.converter.exception.IncorrectFormatExtensionException;
import com.bulish.melnikov.converter.fabric.ConverterFactory;
import com.bulish.melnikov.converter.fabric.Fabric;
import com.bulish.melnikov.converter.model.ConvertRequestMsgDTO;
import com.bulish.melnikov.converter.model.ConvertResponseMsgDTO;
import com.bulish.melnikov.converter.model.ExtensionDto;
import com.bulish.melnikov.converter.model.StatusMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "feature.converter.enabled", havingValue = "true", matchIfMissing = false)
public class ConverterServiceImpl implements ConverterService {

    private final ConverterFactory converterFactory;
    private final ExtensionService extensionService;
    private final StreamBridge streamBridge;

    @Value("${convert.response.destination}")
    private String destination;

    @Value("${convert.status.destination}")
    private String statusDestination;

    @Override
    public void convert(ConvertRequestMsgDTO request) {
        List<ExtensionDto> extensions = extensionService.getAllowedExtensions();
        String formatTo = request.getFormatTo();
        String formatFrom = request.getFormatFrom();

        if (extensions.stream().noneMatch(e -> e.getFormatFrom().equals(formatFrom)
                && e.getFormatsTo().contains(formatTo))) {

            streamBridge.send(statusDestination, StatusMessageDTO.builder()
                    .id(request.getId())
                    .state(State.IN_ERROR)
                    .build());

            throw new IncorrectFormatExtensionException("Check available formats, format from "
                    + formatFrom + " to  " +  formatTo + " is not supported");
        }

        Fabric fabric = converterFactory.getFactory(request.getFormatFrom());
        Converter converter = fabric.getConverter(request.getFormatTo());

        streamBridge.send(statusDestination, StatusMessageDTO.builder()
                .id(request.getId())
                .state(State.CONVERTING)
                .build());

        byte[] convertedFile;

        try {
            convertedFile = converter.convert(request.getFile());

            if (convertedFile.length != 0) {
                log.info("converted file length " + convertedFile.length);
                log.info("send event to destination " + destination);

                streamBridge.send(statusDestination, StatusMessageDTO.builder()
                        .id(request.getId())
                        .state(State.CONVERTED)
                        .build());

                streamBridge.send(destination, ConvertResponseMsgDTO.builder()
                        .file(convertedFile)
                        .formatFrom(formatFrom)
                        .formatTo(formatTo)
                        .id(request.getId())
                        .build());
            } else {
                throw new RuntimeException("converted file array is empty");
            }

        } catch (Exception e) {
            streamBridge.send(statusDestination, StatusMessageDTO.builder()
                    .id(request.getId())
                    .state(State.IN_ERROR)
                    .build());

            throw new RuntimeException("Error converting file", e);
        }
    }
}
