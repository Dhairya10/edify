package com.edify.learning.presentation.components

import androidx.lifecycle.ViewModel
import com.edify.learning.data.service.GemmaService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TranslationViewModel @Inject constructor(
    val gemmaService: GemmaService
) : ViewModel()
