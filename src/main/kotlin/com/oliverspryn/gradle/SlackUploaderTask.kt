package com.oliverspryn.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.nio.file.Paths

open class SlackUploaderTask : DefaultTask() {

    // region Configuration

    var extension: SlackUploaderExtension? = null
    var projectRoot: String? = null

    // endregion

    @TaskAction
    fun doUpload() {
        val fullPath = Paths.get(projectRoot, extension?.filePath).toAbsolutePath()

        println("Comment: ${extension?.comment}")
        println("Channels: ${extension?.channels?.joinToString()}")
        println("File to Upload: $fullPath")
    }
}
