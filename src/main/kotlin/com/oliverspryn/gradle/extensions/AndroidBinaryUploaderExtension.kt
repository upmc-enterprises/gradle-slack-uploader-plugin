package com.oliverspryn.gradle.extensions

open class AndroidBinaryUploaderExtension {

    var channels: List<String>? = null
    var comment: String? = null
    var enabled = true
    var moduleName = "app"
    var token: String? = null

    fun channels(vararg channels: String) {
        this.channels = channels.toList()
    }
}
