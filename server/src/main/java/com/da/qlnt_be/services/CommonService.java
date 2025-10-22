package com.da.qlnt_be.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CommonService {
    Map<?, ?> uploadImage(MultipartFile file, Map<String, Object> options) throws IOException;
    Map<String, Object> deleteImage(String publicId) throws IOException;
}
