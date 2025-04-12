package com.example.ass2.data

import com.example.ass2.api.ApiClient
import com.example.ass2.network.AdvancedResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdvancedFeatureRepository {
    suspend fun fetchAdvancedFeatures(): Result<List<AdvancedResponse>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = ApiClient.service.getAdvancedFeatures()
                if (response.isSuccessful && response.body() != null) {
                    // 从包装对象中提取 suggestions 列表
                    Result.success(response.body()!!.suggestions)
                } else {
                    Result.failure(Exception("Error: ${response.errorBody()?.string()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
