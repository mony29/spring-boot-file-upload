package com.example.fileupload.controller;

import com.example.fileupload.model.FileResponse;
import com.example.fileupload.service.FileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) throws IOException {
        var response = new FileResponse<>();
        String fileName = fileService.fileUpload(file);
        return ResponseEntity.ok(
                new FileResponse<>(
                        "200",
                        "Upload success",
                        fileName
                )
        );
    }
    @GetMapping("/getFile/{fileName}")
    public ResponseEntity<?> getFile(@RequestParam("fileName") String fileName) throws IOException {
        return fileService.getFile(fileName);
    }
    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileResponse<?> uploadMultiFile(@RequestPart(value = "files" , required = false) MultipartFile[] files) throws IOException {
        return fileService.uploadMultiFile(files);
    }
}
