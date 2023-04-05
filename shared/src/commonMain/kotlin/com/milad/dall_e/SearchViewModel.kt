package com.milad.dall_e

import com.milad.dall_e.network.DallEApiImpl
import com.milad.dall_e.network.model.Data
import com.milad.dall_e.network.model.RequestBody
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel constructor(private val network: DallEApiImpl) : ViewModel() {

    private val _generatedImages = MutableStateFlow<SearchState>(SearchState.Loading)
    val generatedImages get() = _generatedImages.asStateFlow()

    fun generateImage(body: RequestBody) {
        viewModelScope.launch (Dispatchers.IO){
            network.generateImage(body).collect { result ->
                result.onSuccess {
                    _generatedImages.value = SearchState.Success(it)
                }.onFailure {
                    _generatedImages.value = SearchState.Error(it)
                }
            }
        }
    }
}

sealed interface SearchState {
    object Loading : SearchState
    data class Error(val throwable: Throwable) : SearchState
    data class Success(val data: List<Data>) : SearchState
}

enum class ImageSize(
    val value: String
) {
    X256("256x256"), X512("512x512"), X1024("1024x1024");
}