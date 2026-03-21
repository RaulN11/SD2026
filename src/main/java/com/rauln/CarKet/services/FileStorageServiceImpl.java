package com.rauln.CarKet.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    @Override
    public String saveFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get("src/main/resources/static/uploads/");
        if (!Files.exists(uploadPath)) { Files.createDirectories(uploadPath); }
        Files.copy(file.getInputStream(), uploadPath.resolve(fileName));
        return "/uploads/" + fileName;
    }

}
