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

    private val _generatedImages = MutableStateFlow<List<Data>?>(emptyList())
    val generatedImages get() = _generatedImages.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error get() = _error.asStateFlow()

    fun generateImage(s: String) {
        val body = RequestBody(1, s, "256x256")
        viewModelScope.launch {
            network.generateImage(body).collect { result ->
                result.onSuccess {
                    _generatedImages.value = it
                }.onFailure {
                    _error.value = it.message
                }
            }
        }
    }
}