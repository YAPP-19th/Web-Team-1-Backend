package com.yapp.giljob.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("https://giljob.netlify.app", "http://www.giljob.co.kr", "http://localhost:5050")
            .allowedMethods(
                HttpMethod.GET.name,
                HttpMethod.PATCH.name,
                HttpMethod.POST.name,
                HttpMethod.DELETE.name
            )
    }
}