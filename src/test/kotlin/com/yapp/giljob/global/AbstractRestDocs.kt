package com.yapp.giljob.global

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@ExtendWith(RestDocumentationExtension::class)
@MockBean(JpaMetamodelMappingContext::class)
abstract class AbstractRestDocs {
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp(
        webApplicationContext: WebApplicationContext,
        restDocumentationContextProvider: RestDocumentationContextProvider
    ) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters<DefaultMockMvcBuilder>(CharacterEncodingFilter(Charsets.UTF_8.name(), true))
            .apply<DefaultMockMvcBuilder>(
                documentationConfiguration(restDocumentationContextProvider).uris()
                    .withScheme("https")
                    .withHost("docs.api.com")
                    .and()
                    .operationPreprocessors()
                    .withRequestDefaults(Preprocessors.prettyPrint())
                    .withResponseDefaults(Preprocessors.prettyPrint())
            )
            .build()
    }
}

