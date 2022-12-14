package com.javatechie.storageapi.controller;

import com.javatechie.storageapi.service.ImageDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/images")
@RequiredArgsConstructor
public class ImageDataController {

    private final ImageDataService imageDataService;

    @GetMapping("{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = imageDataService.downloadImage(fileName);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        String uploadImage = imageDataService.uploadImage(file);

        return ResponseEntity.status(HttpStatus.OK)
               .body(uploadImage);
    }
}
