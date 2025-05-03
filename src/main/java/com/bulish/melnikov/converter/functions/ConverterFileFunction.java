package com.bulish.melnikov.converter.functions;

import com.bulish.melnikov.converter.model.ConvertRequestMsgDTO;
import com.bulish.melnikov.converter.service.ConverterRequestQueueManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(name = "function.converter.enabled", havingValue = "true", matchIfMissing = false)
public class ConverterFileFunction {

    private final ConverterRequestQueueManagerService queueService;

    @Bean
    public Consumer<ConvertRequestMsgDTO> convert() {
        return convertRequest -> {
            log.info("Request to convert arrived : from {} to {}",
                    convertRequest.getFormatFrom(), convertRequest.getFormatTo());
            queueService.addRequestToQueue(convertRequest);
        };
    }
}
