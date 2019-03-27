package com.oliverspryn.gradleslackuploader

data class GradleSlackPluginExtension(
    val channels: List<String>,
    val comment: String? = null,
    val filePath: String
)
