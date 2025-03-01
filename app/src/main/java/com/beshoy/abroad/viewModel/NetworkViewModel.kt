package com.beshoy.abroad.viewModel

import androidx.lifecycle.ViewModel
import com.beshoy.abroad.utils.NetworkConnectionService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val networkService: NetworkConnectionService
) : ViewModel() {

    val isConnected: StateFlow<Boolean> = networkService.isConnected

    fun registerNetworkCallback() {
        networkService.registerCallback()
    }

    fun unregisterNetworkCallback() {
        networkService.unregisterCallback()
    }
}
