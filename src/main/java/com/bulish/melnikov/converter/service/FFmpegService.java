package com.bulish.melnikov.converter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class FFmpegService {

    private final FileService fileService;

    private final Environment environment;

    public byte[] convert(byte[] file, String formatFrom, String formatTo) {
        File tempInputFile = null;
        Path tempOutputFile = null;

        try {
            tempInputFile = File.createTempFile("input-" + UUID.randomUUID(), "." + formatFrom);
            tempOutputFile = Paths.get("temp/files/" + UUID.randomUUID() + "." + formatTo);

            try(BufferedOutputStream buff = new BufferedOutputStream(Files.newOutputStream(tempInputFile.toPath()))) {
                buff.write(file);
                buff.flush();
            }

            String[] command = {
                    "ffmpeg",
                    "-i", tempInputFile.getAbsolutePath(),
                    "-f", formatTo,
                    tempOutputFile.toString()
            };

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            log.info("Convert process has started with command: {}", String.join(" ", command));

            log.info("convert process has started");
            Process process = processBuilder.start();

            if (environment.acceptsProfiles("dev")) loggingConverting(process);

            boolean finished = process.waitFor(2, TimeUnit.MINUTES);

            if (!finished) {
                process.destroy();
                log.error("FFmpeg process timed out");
            } else {
                log.info("FFmpeg process finished successfully");
            }

            try (FileInputStream fis = new FileInputStream(tempOutputFile.toString())) {
                byte[] convertedFile = fis.readAllBytes();

                fileService.deleteFile(tempInputFile.getPath(), tempOutputFile.toString());
                log.info("converted byte array size " + convertedFile.length);

                return convertedFile;
            }

        } catch (IOException exception) {
            log.debug("Error while create input/output files");
        } catch (InterruptedException ex) {
            log.debug("Error while process converting");
        }

        return null;
    }

    public void loggingConverting(Process process) {
        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.info("FFmpeg: {}", line);
                }
            } catch (IOException e) {
                log.error("Error reading FFmpeg output", e);
            }
        }).start();
    }
}