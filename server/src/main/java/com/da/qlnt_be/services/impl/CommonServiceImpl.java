package com.da.qlnt_be.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.da.qlnt_be.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private Cloudinary cloudinary;

    public Map<?, ?> uploadImage(MultipartFile file, Map<String, Object> options) throws IOException {
        Map<String, Object> defaultOptions = new HashMap<>();
        defaultOptions.put("use_filename", false); // Giữ nguyên tên file khi upload
        defaultOptions.put("unique_filename", true); // Tạo tên file duy nhất để tránh trùng lặp
        defaultOptions.put("resource_type", "image"); // Chỉ định loại tài nguyên là hình ảnh
        defaultOptions.put("overwrite", false); // Không ghi đè nếu file đã tồn tại
        defaultOptions.put("folder", "general"); // Thư mục lưu trữ trên Cloudinary

        // Truyền tên file thật để hiển thị đúng trong Cloudinary
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !originalFilename.isEmpty()) {
            // chỉ tên gốc, bỏ đuôi .jpg/.png nếu có
            String filenameWithoutExt = originalFilename.replaceAll("\\.[^.]+$", "");
            defaultOptions.put("display_name", filenameWithoutExt);
        }

        if (options != null) defaultOptions.putAll(options);
        return cloudinary.uploader().upload(file.getBytes(), defaultOptions);
    }

    public Map<String, Object> deleteImage(String publicId) throws IOException {
        // 1. Xóa trên Cloudinary
        Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

        return result;
    }
}

/**
 * {
 *     "asset_folder": "general",
 *     "signature": "a215fa63b7d1cb589b63f99b75ca9e07d58bfcc3",
 *     "format": "jpg",
 *     "resource_type": "image",
 *     "secure_url": "https://res.cloudinary.com/dumrmr6xa/image/upload/v1759848492/general/file_bzgiob.jpg",
 *     "created_at": "2025-10-07T14:48:12Z",
 *     "asset_id": "aba0067090b3868557d815a46a4fc6d3",
 *     "version_id": "03c565e6c439bcf1fefe80f49e4a9ef2",
 *     "type": "upload",
 *     "display_name": "ip15_3",
 *     "version": 1759848492,
 *     "url": "http://res.cloudinary.com/dumrmr6xa/image/upload/v1759848492/general/file_bzgiob.jpg",
 *     "public_id": "general/file_bzgiob",
 *     "tags": [],
 *     "existing": false,
 *     "original_filename": "file",
 *     "api_key": "712522473725651",
 *     "bytes": 4420,
 *     "width": 225,
 *     "etag": "721ac4ec771404dca8aac98703f18b1f",
 *     "placeholder": false,
 *     "height": 225
 * }
 */