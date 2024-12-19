package com.keca.AirVentureBack.upload.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;


@Service
public class UploadScalewayService {

    private final S3Client s3Client;
    private final String bucketName; 

    public UploadScalewayService(
        @Value("${scaleway.s3.access-key}")String accessKey,
        @Value("${scaleway.s3.secret-key}")String secretKey,
        @Value("${scaleway.s3.region}")String region,
        @Value("${scaleway.s3.endpoint}")String endpoint,
        @Value("${scaleway.s3.bucket-name}")String bucketName
    ) {
        this.s3Client = S3Client.builder()
        .region(Region.of(region))
        .endpointOverride(URI.create(endpoint))
        .credentialsProvider(StaticCredentialsProvider
        .create(AwsBasicCredentials.create(accessKey, secretKey)))
        .build();

        this.bucketName = bucketName;
    }

 
    public String uploadOneFile(MultipartFile file, Long identifier, String folder) {
        try {
            // Créer un chemin unique pour le fichier
            String key = folder + "/" + identifier + "/" + file.getOriginalFilename();
            
            // Utiliser InputStream et RequestBody pour envoyer le fichier
            InputStream inputStream = file.getInputStream();
            
            s3Client.putObject(
                PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build(),
                RequestBody.fromInputStream(inputStream, file.getSize())
            );
            
            // Retourner l'URL du fichier
            return "https://" + bucketName + ".scw.cloud/" + key;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du téléchargement du fichier : " + e.getMessage(), e);
        }
    }

    public List<String> uploadFiles(List<MultipartFile> files, Long identifier, String folder) {
        List<String> fileUrls = new ArrayList<>();
        
        for (MultipartFile file : files) {
            // Réutiliser la méthode pour un fichier unique
            String fileUrl = uploadOneFile(file, identifier, folder);
            fileUrls.add(fileUrl);
        }
        
        return fileUrls;
    }
    
    public File convertToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        return file;
    }

}
