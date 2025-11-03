package com.example.jwtexample.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

@Configuration
@ConfigurationProperties(prefix = "minio")
@Data
public class FileProperties {

    private String bucket;
    private String url;
    private String accessKey;
    private String secretKey;
    private DataSize maxSize;
}