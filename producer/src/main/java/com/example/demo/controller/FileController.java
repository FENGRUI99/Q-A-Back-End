package com.example.demo.controller;


import com.example.demo.configuration.ResponseMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class FileController {

    @RequestMapping("/img")
    public ResponseMessage upload(@RequestBody() MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return ResponseMessage.fail();
        }
        String fileName= file.getOriginalFilename();
        fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName;
        String path ="/resources/image"+fileName;
        File dest = new File(path);
        file.transferTo(dest);
        return ResponseMessage.success();
    }
}
