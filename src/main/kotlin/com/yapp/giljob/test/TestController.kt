package com.yapp.giljob.test
import com.yapp.giljob.global.dto.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/test")
@RestController
class TestController {

    @GetMapping
    fun getTest(): ResponseEntity<BaseResponse<String>> {
        return ResponseEntity.ok(BaseResponse.of(HttpStatus.OK, "Spring Rest Docs Test", "Test Data"))
    }

}
