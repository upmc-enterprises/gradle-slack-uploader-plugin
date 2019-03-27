package com.oliverspryn.gradleslackuploader

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.nio.file.Paths

class GradleSlackUploaderPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create(this::class.java.simpleName, GradleSlackPluginExtension::class.java)

        project.tasks.create("gradleSlackUploader", GradleSlackUploaderTask::class.java) { task ->
            task.channels = extension.channels
            task.comment = extension.comment
            task.file = Paths.get(project.projectDir.absolutePath, extension.filePath).toFile()

            println(task.file)
        }
    }
}
