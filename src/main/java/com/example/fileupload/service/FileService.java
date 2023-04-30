package com.example.fileupload.service;

import com.example.fileupload.model.FileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String fileUpload(MultipartFile file) throws IOException;
    ResponseEntity<?> getFile(String fileName) throws IOException;
    FileResponse<?> uploadMultiFile(MultipartFile[] files) throws IOException;
}
