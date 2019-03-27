package com.oliverspryn.gradleslackuploader

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File


class GradleSlackUploaderTask : DefaultTask() {

    // region Configuration

    var channels: List<String>? = null
    var comment: String? = null
    var file: File? = null

    // endregion

    @TaskAction
    fun doUpload() {
    }
}
