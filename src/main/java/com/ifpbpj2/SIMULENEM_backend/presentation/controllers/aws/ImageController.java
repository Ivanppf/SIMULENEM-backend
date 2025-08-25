package com.ifpbpj2.SIMULENEM_backend.presentation.controllers.aws;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ifpbpj2.SIMULENEM_backend.infra.aws.S3Service;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.ImageResponseDTO;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final S3Service s3Service;

    public ImageController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageResponseDTO> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String imageUrl = s3Service.uploadImage(file);
        return ResponseEntity.ok(new ImageResponseDTO(imageUrl));
    }
}