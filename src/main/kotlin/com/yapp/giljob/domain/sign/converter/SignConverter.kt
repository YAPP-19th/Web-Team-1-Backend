package com.yapp.giljob.domain.sign.converter

import com.yapp.giljob.domain.sign.dto.request.SignUpRequest
import com.yapp.giljob.domain.user.domain.User
import org.mapstruct.*


@Mapper(componentModel = "spring")
interface SignConverter {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun signUpConvertToModel(signUpRequest: SignUpRequest): User
}