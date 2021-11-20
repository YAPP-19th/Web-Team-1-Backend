package com.yapp.giljob.infra.s3.api

import com.yapp.giljob.global.common.dto.BaseResponse
import com.yapp.giljob.infra.s3.application.S3Service
import com.yapp.giljob.infra.s3.dto.responsne.S3UploadResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/upload")
class S3Controller(
    private val s3Service: S3Service
) {

    @PostMapping
    fun upload(@RequestParam("file") file: MultipartFile): ResponseEntity<BaseResponse<S3UploadResponseDto>>{
        return ResponseEntity.ok(
            BaseResponse.of(HttpStatus.OK, "썸네일 업로드 성공입니다.", s3Service.fileUplaod(file))
        )
    }

}