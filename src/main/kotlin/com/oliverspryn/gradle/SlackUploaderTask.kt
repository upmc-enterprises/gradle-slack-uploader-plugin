package com.oliverspryn.gradle

import allbegray.slack.webapi.SlackWebApiClientImpl
import com.oliverspryn.gradle.exceptions.FileUploadException
import com.oliverspryn.gradle.exceptions.MissingChannelException
import com.oliverspryn.gradle.exceptions.MissingFilePathException
import com.oliverspryn.gradle.exceptions.MissingTokenException
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.nio.file.Paths
import javax.inject.Inject

open class SlackUploaderTask @Inject constructor(
    private val extension: SlackUploaderExtension
) : DefaultTask() {

    @TaskAction
    fun doUpload() {
        if (!extension.enabled) return

        val channels = extension.channels?.toMutableList() ?: throw MissingChannelException()
        val filePath = extension.filePath ?: throw MissingFilePathException()
        val token = extension.token ?: throw MissingTokenException()

        val file = Paths.get(project.rootDir.absolutePath, filePath).toFile()
        val slack = SlackWebApiClientImpl(token)

        try {
            channels.forEach {
                slack.uploadFile(
                    file,
                    file.extension,
                    file.name,
                    file.name,
                    extension.comment ?: "",
                    it
                )
            }
        } catch (e: Exception) {
            throw FileUploadException(e.localizedMessage)
        }
    }
}
