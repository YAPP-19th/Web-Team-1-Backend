package com.yapp.giljob.infra.s3.application

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.yapp.giljob.global.error.ErrorCode
import com.yapp.giljob.global.error.exception.BusinessException
import com.yapp.giljob.infra.s3.dto.responsne.S3UploadResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class S3Service(
    private val amazonS3: AmazonS3
){
    @Value("\${cloud.aws.s3.bucket}")
    private lateinit var bucketName: String

    fun fileUpload(file: MultipartFile): S3UploadResponseDto {

        val fileName = UUID.randomUUID().toString() + file.originalFilename
        val metadata = ObjectMetadata()
        metadata.contentLength = file.size

        runCatching {
            amazonS3.putObject(bucketName, fileName, file.inputStream, metadata)
        }.onFailure {
            throw BusinessException(ErrorCode.FILE_UPLOAD_ERROR)
        }

        return S3UploadResponseDto(
            fileUrl = amazonS3.getUrl(bucketName, fileName).toString()
        )
    }
}