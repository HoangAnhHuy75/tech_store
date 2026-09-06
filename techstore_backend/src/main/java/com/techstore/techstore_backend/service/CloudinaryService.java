package com.techstore.techstore_backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile (MultipartFile file) throws Exception{
        Map uploadResult =cloudinary.uploader().upload(file.getBytes(),
                ObjectUtils.asMap("folder","tech_store"));
        return (String) uploadResult.get("secure_url");
    }
}
