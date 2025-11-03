package com.example.jwtexample.fileservice;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileUploadResponseDto uploadFile(MultipartFile file, String folderType);

    MediaType detectMediaType(String fileName);
}
