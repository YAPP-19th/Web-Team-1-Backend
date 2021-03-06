package com.yapp.giljob.global.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String
) {
    // Common
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "Internal Server Error"),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C002", "Invalid Input Value"),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C003", "Invalid Type Value"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C004", "Method Not Allowed"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C005", "Entity Not Found"),

    // Token
    NO_TOKEN_ERROR(HttpStatus.FORBIDDEN, "T001", "No Token Error"),
    INVALID_TOKEN_ERROR(HttpStatus.FORBIDDEN, "T002", "Invalid Token Error"),
    EXPIRED_TOKEN_ERROR(HttpStatus.FORBIDDEN, "T003", "Expired Token Error"),

    // Sign
    CAN_NOT_GET_KAKAO_ID_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S001", "Can Not Get Kakao ID Error"),
    ALREADY_SIGN_UP_USER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S002", "Already Sign Up User Error"),

    // Quest
    ALREADY_PARTICIPATED_QUEST(HttpStatus.BAD_REQUEST, "Q001", "Already Participated Quest"),
    ALREADY_COMPLETED_QUEST(HttpStatus.BAD_REQUEST, "Q002", "Already Completed Quest"),
    NOT_COMPLETED_QUEST(HttpStatus.BAD_REQUEST, "Q003", "Not Completed Quest"),
    NOT_PARTICIPATED_QUEST(HttpStatus.BAD_REQUEST, "Q005", "Not Participated Quest"),

    // SubQuest
    ALREADY_COMPLETED_SUBQUEST(HttpStatus.BAD_REQUEST, "SQ001", "Already Completed SubQuest"),
    NOT_COMPLETED_SUBQUEST(HttpStatus.BAD_REQUEST, "SQ002", "Not Completed SubQuest"),
    NOT_PARTICIPATED_SUBQUEST(HttpStatus.BAD_REQUEST, "SQ003", "Not Participated SubQuest"),

    // Roadmap
    ALREADY_SCRAPED_ROADMAP(HttpStatus.BAD_REQUEST, "R001", "Already Scraped Roadmap"),
    CAN_NOT_DELETE_ROADMAP(HttpStatus.BAD_REQUEST, "R002", "Can Not Delete Roadmap"),

    // Position
    POSITION_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "Position Not Found"),

    // File Upload
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "F001", "File Upload Error"),

    // Quest Participation
    QUEST_PARTICIPATION_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "QP001", "Quest Participation Not Found")
}