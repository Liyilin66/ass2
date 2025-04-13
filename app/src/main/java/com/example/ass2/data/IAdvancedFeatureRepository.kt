package com.example.ass2.data

import com.example.ass2.ViewModel.OperationResult
import com.example.ass2.network.AdvancedResponse

interface IAdvancedFeatureRepository {
    suspend fun fetchAdvancedFeatures(): OperationResult<List<AdvancedResponse>>
}
