package com.apple.shop.domain.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class S3Service {

  //
  @Value("${spring.cloud.aws.s3.bucket}") //application.properties에 있는 버킷명 가져와서 아래 버킷 변수에 넣어달라는 어노테이션
  private String bucket;
  private final S3Presigner s3Presigner;

  public String createPresignedUrl(String path) {
    var putObjectRequest = PutObjectRequest.builder()
           .bucket(bucket)  // 올릴 버킷명
           .key(path)       // 경로
           .build();
    var preSignRequest = PutObjectPresignRequest.builder()
           .signatureDuration(Duration.ofMinutes(3)) //url 유효기간
           .putObjectRequest(putObjectRequest)
           .build();
    return s3Presigner.presignPutObject(preSignRequest).url().toString(); //presigned url Return
 }

}
