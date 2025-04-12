package com.example.ass2.network

import retrofit2.Response
import retrofit2.http.GET

// 原始数据模型保持不变
data class AdvancedResponse(
    val title: String,
    val description: String
)

// 新增包装类，用于解析顶层 JSON 对象中的 suggestions 字段
data class AdvancedResponseWrapper(
    val suggestions: List<AdvancedResponse>
)

interface AdvancedApiService {
    @GET("data.json")
    suspend fun getAdvancedFeatures(): Response<AdvancedResponseWrapper>
}
