package com.inno.coogle.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.inno.coogle.global.error.exception.ErrorCode;
import com.inno.coogle.global.error.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AmazonS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> uploadFile(List<MultipartFile> multipartFiles, String s3Directory) {
        List<String> imageUrlList = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            //fileName 에 파라미터로 들어온 파일의 이름을 할당.
            String fileName = file.getOriginalFilename();
            fileName = UUID.randomUUID().toString().concat(getImgExtension(fileName));
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());
            try (InputStream inputStream = file.getInputStream()){
                amazonS3Client.putObject(new PutObjectRequest(bucket, s3Directory+fileName, inputStream, objectMetadata));
                imageUrlList.add(amazonS3Client.getUrl(bucket, s3Directory+fileName).toString());
            } catch (IOException e) {
                throw new InvalidValueException(ErrorCode.UPLOAD_FAILED);
            }
        }
        return imageUrlList;
    }

    //이미지 확장자 확인
    private String getImgExtension(String fileName) { // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였습니다.
        ArrayList<String> fileValidate = new ArrayList<>();
        fileValidate.add(".png");
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        String idxFileName = fileName.substring(fileName.lastIndexOf("."));
        if (!fileValidate.contains(idxFileName)) {
            System.out.println("idxFileName = " + idxFileName);
            throw new InvalidValueException(ErrorCode.INVALID_FILE_EXTENSION);
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
