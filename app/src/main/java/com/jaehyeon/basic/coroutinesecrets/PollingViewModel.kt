package com.jaehyeon.basic.coroutinesecrets

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by jaehyeon.
 * Date: 2024. 8. 27.
 */
class PollingViewModel: ViewModel() {

    var isPolling by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            isPolling = true
            fetchNetwork(HttpClient.client)
            isPolling = false
        }
    }
}