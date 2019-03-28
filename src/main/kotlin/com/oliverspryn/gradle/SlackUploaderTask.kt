package com.oliverspryn.gradle

import com.github.seratch.jslack.Slack
import com.github.seratch.jslack.api.methods.request.files.FilesUploadRequest
import com.oliverspryn.gradle.exceptions.FileUploadException
import com.oliverspryn.gradle.exceptions.MissingChannelException
import com.oliverspryn.gradle.exceptions.MissingFilePathException
import com.oliverspryn.gradle.exceptions.MissingTokenException
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

        if (extension?.enabled != true) return
        validateConfiguration()

        val file = Paths.get(projectRoot, extension?.filePath).toFile()
        val slack = Slack.getInstance()

        val uploadRequest = FilesUploadRequest.builder()
            .channels(extension?.channels)
            .file(file)
            .filename("${file.name}.${file.extension}")
            .initialComment(extension?.comment ?: "")
            .token(extension?.token)
            .build()

        val result = slack.methods().filesUpload(uploadRequest)

        if (!result.isOk) {
            throw FileUploadException(result.error)
        }
    }

    private fun validateConfiguration() {
        if (extension?.channels.isNullOrEmpty()) throw MissingChannelException()
        if (extension?.filePath.isNullOrBlank()) throw MissingFilePathException()
        if (extension?.token.isNullOrBlank()) throw MissingTokenException()
    }
}
