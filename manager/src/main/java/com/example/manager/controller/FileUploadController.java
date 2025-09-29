package com.example.manager.controller;

import com.example.manager.aspect.SystemLogger;
import com.example.manager.entity.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@CrossOrigin("*")
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {

        String originalFilename = file.getOriginalFilename();
        String filename= UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        file.transferTo(new File("E:\\新闻浏览平台\\manager\\src\\main\\resources\\static\\images\\"+filename));
        return Result.success(filename);
    }
}

