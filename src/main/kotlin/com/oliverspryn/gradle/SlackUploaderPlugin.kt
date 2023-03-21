package com.oliverspryn.gradle

import com.android.build.api.variant.AndroidComponentsExtension
import com.oliverspryn.gradle.extensions.AndroidBinaryUploaderExtension
import com.oliverspryn.gradle.extensions.BasicFileUploaderExtension
import com.oliverspryn.gradle.tasks.BasicUploaderTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.UnknownDomainObjectException

class SlackUploaderPlugin : Plugin<Project> {

    @Suppress("UnstableApiUsage") // create() is incubating
    override fun apply(project: Project) {
        // Basic file uploader
        val basicUploaderExtension = project.extensions.create("uploadFileToSlack", BasicFileUploaderExtension::class.java)
        project.tasks.create("uploadFileToSlack", BasicUploaderTask::class.java, basicUploaderExtension)

        // Android-specific file uploader
        try {
            val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)

            androidComponents.onVariants { variant ->
                val assembledUploaderExtension = project.extensions.create(
                    "uploadAndroidBinaryToSlack",
                    AndroidBinaryUploaderExtension::class.java
                )

                val assembledFilePath = "${assembledUploaderExtension.moduleName}/build/outputs/apk/${variant.name}/app-${variant.name}.apk"

                project.tasks.create(
                    "uploadAssembled${variant.name.capitalize()}ToSlack",
                    BasicUploaderTask::class.java,
                    assembledUploaderExtension,
                    assembledFilePath
                )

                val bundledUploaderExtension = project.extensions.create(
                    "uploadBundledBinaryToSlack",
                    AndroidBinaryUploaderExtension::class.java
                )

                val bundledFilePath = "${assembledUploaderExtension.moduleName}/build/outputs/bundle/${variant.name}/app-${variant.name}.aab"

                project.tasks.create(
                    "uploadAssembled${variant.name.capitalize()}ToSlack",
                    BasicUploaderTask::class.java,
                    bundledUploaderExtension,
                    bundledFilePath
                )
            }
        } catch (e: UnknownDomainObjectException) {
            println("Slack uploader plugin has detected that this is not an Android project")
        }
    }
}
