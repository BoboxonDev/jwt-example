package com.example.jwtexample.fileservice;

import com.example.jwtexample.common.exception.FileStorageException;
import com.example.jwtexample.minio.FileProperties;
import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Duration;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final MinioClient minioClient;
    private final FileProperties fileProperties;

    @Override
    public FileUploadResponseDto uploadFile(MultipartFile file, String folderType) {
        log.info("Received file: {}", file.getOriginalFilename());

        try {
            if (file.isEmpty()) {
                throw new FileStorageException("Empty file received", HttpStatus.BAD_REQUEST);
            }

            if (folderType == null || folderType.isBlank()) {
                throw new FileStorageException("Folder type cannot be empty", HttpStatus.BAD_REQUEST);
            }

            if (file.getSize() > fileProperties.getMaxSize().toBytes()) {
                throw new FileStorageException("File too large. Max allowed: " + fileProperties.getMaxSize(),
                        HttpStatus.BAD_REQUEST);
            }

            String bucket = fileProperties.getBucket();
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
                log.info("Created new bucket: {}", bucket);
            }

            String originalName = file.getOriginalFilename();
            String safeName = (originalName == null ? "file" : originalName).replaceAll("\\s+", "_");
            String uniqueName = UUID.randomUUID() + "_" + safeName;
            String objectName = folderType + "/" + uniqueName;

            try (InputStream input = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucket)
                                .object(objectName)
                                .stream(input, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }

            String presignedUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucket)
                            .object(objectName)
                            .expiry((int) Duration.ofHours(24).getSeconds())
                            .build()
            );

            FileUploadResponseDto response = new FileUploadResponseDto();
            response.setFileName(originalName);
            response.setFileType(file.getContentType());
            response.setObjectName(objectName);
            response.setFilePath(objectName);
            response.setUrl(presignedUrl);

            double sizeInMb = (double) file.getSize() / (1024 * 1024);
            response.setFileSize(String.format("%.3f MB", sizeInMb));

            log.info("Uploaded file successfully: {}", objectName);
            return response;

        } catch (MinioException e) {
            log.error("MinIO error: {}", e.getMessage(), e);
            throw new FileStorageException("MinIO error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("File upload failed: {}", e.getMessage(), e);
            throw new FileStorageException("Failed to upload file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public MediaType detectMediaType(String fileName) {
        String lower = fileName.toLowerCase();
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return MediaType.IMAGE_JPEG;
        if (lower.endsWith(".png")) return MediaType.IMAGE_PNG;
        return MediaType.APPLICATION_OCTET_STREAM;
    }
}
