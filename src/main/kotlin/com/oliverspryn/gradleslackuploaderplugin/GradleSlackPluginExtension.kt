package com.oliverspryn.gradleslackuploaderplugin

data class GradleSlackPluginExtension(
    val channels: List<String>,
    val comment: String? = null,
    val filePath: String
)
