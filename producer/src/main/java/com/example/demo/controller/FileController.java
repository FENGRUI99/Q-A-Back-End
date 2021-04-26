package com.example.demo.controller;


import com.example.demo.configuration.ResponseMessage;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class FileController {

    @RequestMapping(value = "/img" , method = RequestMethod.POST)
    public ResponseMessage upload(@RequestParam List<MultipartFile> files, HttpServletRequest request) throws IOException {
        System.out.println(files.get(0).getBytes());
        System.out.println(files.size());

        String s=Base64Utils.encodeToString(files.get(0).getBytes());
        System.out.println(s);
        System.out.println(s.length());
        return ResponseMessage.success();
    }
}
