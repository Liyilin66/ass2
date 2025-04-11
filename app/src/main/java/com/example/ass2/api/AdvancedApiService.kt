package com.example.ass2.network

import retrofit2.Response
import retrofit2.http.GET

// 用于接收远程数据的实体对象（根据 API 返回数据格式自定义）
data class AdvancedResponse(
    val featureName: String,
    val description: String,
    val isEnabled: Boolean
)

// 新增数据模型用于获取学习建议
data class StudyAdviceResponse(
    val advice: String
)

// 定义 API 接口
interface AdvancedApiService {
    @GET("advancedFeatures")
    suspend fun getAdvancedFeatures(): Response<List<AdvancedResponse>>

    // 新增接口：获取每日学习建议
    @GET("studyAdvice")
    suspend fun getStudyAdvice(): Response<StudyAdviceResponse>
}
