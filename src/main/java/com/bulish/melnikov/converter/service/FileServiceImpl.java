package com.bulish.melnikov.converter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final String dirUpload = "temp/files/";

    @Override
    public String saveMultipartFile(MultipartFile file) throws IOException {
        Path path = getNewPathForFile(file.getOriginalFilename());
        Files.createDirectories(Paths.get(dirUpload));
        file.transferTo(path);

        return path.toString();
    }

    @Override
    public String saveFile(byte[] file, String formatTo, String fileName) throws IOException {
        Path path = getNewPathForFile(getFileNameWithoutExtension(fileName) + "." + formatTo);
        Files.createDirectories(Paths.get(dirUpload));

        try (FileOutputStream out = new FileOutputStream(path.toString())) {
           out.write(file);
           out.flush();
        }

        return path.toString();
    }


    public Path getNewPathForFile(String originalFileName) {
        Path path = Paths.get(dirUpload + originalFileName);
        String newFileName = originalFileName;
        int fileCount = 0;
        String fileNameWithoutExt = getFileNameWithoutExtension(originalFileName);
        String fileExtension = getFileExtension(originalFileName);
        fileExtension = fileExtension.isEmpty() ? "" : "." + fileExtension;

        while (Files.exists(path)) {
            fileCount++;
            newFileName = String.format("%s(%d)%s", fileNameWithoutExt, fileCount, fileExtension);
            path = Paths.get(dirUpload + newFileName);
        }

        return path;
    }

    public String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot > 0 && lastIndexOfDot < fileName.length() - 1) {

            return fileName.substring(lastIndexOfDot + 1);
        }

        return "";
    }

    public String getFileNameWithoutExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return fileName;
        } else {
            return fileName.substring(0, lastDotIndex);
        }
    }

    @Override
    public File getFile(String filePath) {
        return new File(filePath);
    }

    @Override
    public void deleteFile(String... filePaths) throws IOException {

        for (String path : filePaths) {
            if (path != null && Files.exists(Paths.get(path))) {
                Files.delete(Paths.get(path));
            }
        }
    }
}
