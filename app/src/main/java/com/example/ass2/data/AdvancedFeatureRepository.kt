package com.example.ass2.data

import com.example.ass2.api.ApiClient
import com.example.ass2.ViewModel.OperationResult
import com.example.ass2.network.AdvancedResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AdvancedFeatureRepository : IAdvancedFeatureRepository {
    override suspend fun fetchAdvancedFeatures(): OperationResult<List<AdvancedResponse>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = ApiClient.service.getAdvancedFeatures()
                if (response.isSuccessful && response.body() != null) {
                    // 从响应中提取 suggestions 列表
                    OperationResult.Success(response.body()!!.suggestions)
                } else {
                    OperationResult.Failure(Exception("Error: ${response.errorBody()?.string()}"))
                }
            } catch (e: Exception) {
                OperationResult.Failure(e)
            }
        }
    }
}
