package com.spuldz.praksesprojekts.core.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch(
    context = CoroutineExceptionHandler { _, e ->
        Timber.e(e, "Coroutine failed: ${e.localizedMessage}")
    },
    block = block,
)

private val defaultScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

fun launchDefault(block: suspend CoroutineScope.() -> Unit) = defaultScope.launch(
    context = CoroutineExceptionHandler { _, e -> Timber.w(e, "Coroutine failed: ${e.localizedMessage}") },
    block = block
)
