package com.oliverspryn.gradle.tasks

import allbegray.slack.webapi.SlackWebApiClientImpl
import com.oliverspryn.gradle.exceptions.FileUploadException
import com.oliverspryn.gradle.exceptions.MissingChannelException
import com.oliverspryn.gradle.exceptions.MissingFilePathException
import com.oliverspryn.gradle.exceptions.MissingTokenException
import com.oliverspryn.gradle.extensions.AndroidBinaryUploaderExtension
import com.oliverspryn.gradle.extensions.BasicFileUploaderExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.nio.file.Paths
import javax.inject.Inject

open class AndroidBinaryUploaderTask @Inject constructor(
    private val extension: AndroidBinaryUploaderExtension,
    private val filePath: String
) : DefaultTask() {

    @TaskAction
    fun doUpload() {
        if (!extension.enabled) return

        val channels = extension.channels?.toMutableList() ?: throw MissingChannelException()
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
