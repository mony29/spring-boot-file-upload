package com.example.fileupload.service;

import com.example.fileupload.model.FileResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    private final Path root = Paths.get("src/main/resources/images");
    @Override
    public String fileUpload(MultipartFile file) throws IOException {
        try {
            String fileName = file.getOriginalFilename();   //store file upload
            assert fileName != null;
            if (
                    fileName.contains(".jpeg") ||
                            fileName.contains(".png") ||
                            fileName.contains(".jpg") ||
                            fileName.contains(".docx") ||
                            fileName.contains(".pptx") ||
                            fileName.contains(".gif")
            ){
                /***
                 * Generate new uuid file name
                 */
                fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);
                /***
                 * It will create an 'images' directory if not exist
                 */
                if(!Files.exists(root)){
                    Files.createDirectories(root);
                }
                /***
                 *It will replace if that file name already exist
                 */
                Files.copy(file.getInputStream(), this.root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                return fileName;
            }
        }catch (Exception e){
            throw new IOException("Cannot upload file!");
        }
        return null;
    }

    @Override
    public ResponseEntity<?> getFile(String fileName) throws IOException {
        try {
            // find where the file path is
            Path getFileName = Paths.get(root + "/" + fileName);
            // after found file path, convert to byte
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(getFileName));
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(
                    new InputStreamResource(resource.getInputStream())
            );
        }catch (Exception e){
            throw new IOException("Cannot found this file");
        }
    }

    @Override
    public FileResponse<?> uploadMultiFile(MultipartFile[] files) throws IOException {
        try {
            var res = new FileResponse<>();
            List<String> fileName = new ArrayList<>();  // store file upload
            /***
             * loop file input
             */
            for(int i=0; i<files.length; i++){
                String response = fileUpload(files[i]); // get input file 1 by 1
                if(fileName.contains("Incorrect file")){
                    res.setStatus("400");
                    res.setMessage("Failed upload file");
                }else {
                    fileName.add(response);
                    res.setStatus("200");
                    res.setMessage("Upload success");
                    res.setPayload(fileName);
                }
            }
            return res;
        }catch (Exception e){
            throw new IOException("Cannot upload file");
        }
    }
}
