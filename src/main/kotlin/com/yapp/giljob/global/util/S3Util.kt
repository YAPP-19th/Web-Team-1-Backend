package com.yapp.giljob.global.util

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.S3Object
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mock.web.MockMultipartFile
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Component
class S3Util {
    private val log : Logger get() = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var amazonS3: AmazonS3

    @Value("\${cloud.aws.s3.bucket}")
    private lateinit var bucketName: String

    fun fileUplaod(file: MultipartFile): String {
        var fileName = ""
        try {
            fileName = UUID.randomUUID().toString() + file.originalFilename
            val metadata = ObjectMetadata()
            metadata.contentLength = file.size
            amazonS3.putObject(bucketName, fileName, file.inputStream, metadata)
        } catch (e: Exception) {
            throw BusinessException(ErrorCode.FILE_UPLOAD_ERROR)
        }
        log.info("File Uploaded Successfully : {}", fileName)
        return fileName
    }

    fun getFile(fileName: String): MultipartFile {
        val s3Object = amazonS3.getObject(bucketName, fileName)
        return MockMultipartFile(fileName, fileName, s3Object.objectMetadata.contentType, s3Object.objectContent)
    }
}