package com.javatechie.storageapi.service;

import com.javatechie.storageapi.entity.ImageData;
import com.javatechie.storageapi.repository.ImageDataRepository;
import com.javatechie.storageapi.util.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageDataService {

    private final ImageDataRepository imageDataRepository;

    public String uploadImage(MultipartFile file) throws IOException {
        ImageData imageData = imageDataRepository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .build());

        return Optional.ofNullable(imageData)
                .map(fileSaved -> "file uploaded successfully: " + fileSaved.getName())
                .orElse("error in upload file: " + file.getOriginalFilename());
    }

    public byte[] downloadImage(String fileName) {
        return imageDataRepository.findByName(fileName)
                .map(file -> ImageUtils.decompressImage(file.getImageData())).get();
    }
}
