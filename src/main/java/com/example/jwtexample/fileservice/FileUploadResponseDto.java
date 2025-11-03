package com.example.jwtexample.fileservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponseDto {

    private String fileName;
    private String fileType;
    private String filePath;
    private String fileSize;
    private String objectName;
    private String url;
}
