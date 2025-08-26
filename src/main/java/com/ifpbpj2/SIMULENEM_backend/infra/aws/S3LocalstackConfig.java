package com.ifpbpj2.SIMULENEM_backend.infra.aws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class S3LocalstackConfig {

        @Value("${cloud.aws.s3.endpointUrl}")
        private String endpointUrl;

        @Value("${cloud.aws.credentials.accessKey}")
        private String accessKey;

        @Value("${cloud.aws.credentials.secretKey}")
        private String secretKey;

        @Value("${cloud.aws.region.static}")
        private String region;

        @Value("${cloud.aws.s3.bucketName}")
        private String bucketName;

        @Bean
        AmazonS3 amazonS3Client() {
                AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                                .withEndpointConfiguration(
                                                new AwsClientBuilder.EndpointConfiguration(endpointUrl, region))
                                .withCredentials(new AWSStaticCredentialsProvider(
                                                new BasicAWSCredentials(accessKey, secretKey)))
                                .withPathStyleAccessEnabled(true)
                                .build();

                if (!s3Client.doesBucketExistV2(bucketName)) {
                        s3Client.createBucket(bucketName);
                }
                return s3Client;
        }
}
