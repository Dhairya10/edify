package com.edify.learning.data.service

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PromptService @Inject constructor(private val context: Context) {

    private val prompts: Map<String, String> by lazy {
        loadPrompts()
    }

    private fun loadPrompts(): Map<String, String> {
        return try {
            val jsonString = context.assets.open("prompts.json").bufferedReader().use { it.readText() }
            val type = object : TypeToken<Map<String, String>>() {}.type
            Gson().fromJson(jsonString, type)
        } catch (e: IOException) {
            e.printStackTrace()
            emptyMap()
        }
    }

    fun getPrompt(key: String): String {
        return prompts[key] ?: ""
    }

    fun getFormattedPrompt(key: String, vararg args: Pair<String, String>): String {
        var prompt = getPrompt(key)
        args.forEach { (placeholder, value) ->
            prompt = prompt.replace("{$placeholder}", value)
        }
        return prompt
    }
}
