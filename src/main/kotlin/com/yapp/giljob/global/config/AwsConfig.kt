package com.yapp.giljob.global.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class AwsConfig {

    @Autowired
    private lateinit var amazonS3: AmazonS3

    @Value("\${cloud.aws.credentials.access-key}")
    private lateinit var awsAccessKey: String

    @Value("\${cloud.aws.credentials.secret-key}")
    private lateinit var awsSecretKey: String

    @Value("\${cloud.aws.region.static}")
    private lateinit var regions: Regions

    @Primary
    @Bean
    fun amazonSQSAsync(): AmazonS3? {
        return AmazonS3ClientBuilder.standard().withRegion(regions)
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(awsAccessKey, awsSecretKey)))
            .build()
    }

}