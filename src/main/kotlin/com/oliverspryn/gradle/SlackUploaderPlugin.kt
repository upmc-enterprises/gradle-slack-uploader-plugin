package com.oliverspryn.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class SlackUploaderPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val extension = project.extensions.create("uploadFileToSlack", SlackUploaderExtension::class.java)

        project.tasks.create("uploadFileToSlack", SlackUploaderTask::class.java) { task ->
            task.extension = extension
            task.projectRoot = project.projectDir.absolutePath
        }
    }
}
