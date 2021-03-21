package com.example.nuxtback.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    @Value("${upload.path}")
    private String path;

    public String saveFile(MultipartFile file) throws IOException {
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
        String fileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
        file.transferTo(new File(path + '/' + fileName));
        return "http://localhost:3001/img/" + fileName;
    }

}
