package com.bulish.melnikov.converter.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface FileService {

     String saveMultipartFile(MultipartFile file) throws IOException;

     String getFileExtension(String fileName);

     Path getNewPathForFile(String originalFileName);

     String getFileNameWithoutExtension(String fileName);

     File getFile(String filePath);

     String saveFile(byte[] file, String formatTo, String fileName) throws IOException;

     void deleteFile(String... filePaths) throws IOException;
}
