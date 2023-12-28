package com.abcde.cultureStay.restController;

import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.abcde.cultureStay.util.FileService.saveFile;

@RestController
@RequestMapping("image")
public class ImageController {

    @Value("${image.save.location}")
    String saveLocation;

    @Value("${image.getImage.url}")
    String url;

    // 이미지를 저장하고 저장한 이미지를 여러개? return
    @PostMapping("upload")
    public ResponseEntity<?> hander(@RequestParam("upload") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        try {
            String fileName = saveFile(file, saveLocation);
            String fileUrl = url + fileName;

            response.put("uploaded", 1);
            response.put("fileName", fileName);
            response.put("url", fileUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("uploaded", 0);
            response.put("error", Collections.singletonMap("message", "File upload failed"));
        }

        return null;
    }

    @GetMapping("getImage/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path fileStorageLocation = Paths.get(saveLocation);
            Path filePath = fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()){
                return ResponseEntity
                        .ok()
                        .body(resource);
            } else {
                return ResponseEntity
                        .notFound()
                        .build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

    }

}
