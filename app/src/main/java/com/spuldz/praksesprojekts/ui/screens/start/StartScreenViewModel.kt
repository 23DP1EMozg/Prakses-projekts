package com.spuldz.praksesprojekts.ui.screens.start

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StartScreenViewModel @Inject constructor(
    private val startScreenRepository: StartScreenRepository
) : ViewModel() {

}
