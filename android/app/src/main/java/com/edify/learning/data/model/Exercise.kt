package com.edify.learning.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Exercise(
    val question: String,
    val answer: String
) : Parcelable
