package com.da.qlnt_be.controller;

import com.da.qlnt_be.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private CommonService commonService;

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            return ResponseEntity.ok(commonService.uploadImage(file, Map.of()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteImage(@RequestParam String publicId) {
        try {
            Map<String, Object> result = commonService.deleteImage(publicId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}
