package com.oliverspryn.gradle.extensions

open class BasicFileUploaderExtension {

    var channels: List<String>? = null
    var comment: String? = null
    var enabled = true
    var filePath: String? = null
    var token: String? = null

    fun channels(vararg channels: String) {
        this.channels = channels.toList()
    }
}
