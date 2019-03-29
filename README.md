<div align="center">
<img src="docs/banner.png" />

<br>
<br>

<h1>Gradle Slack Uploader Plugin<br/><sub>Upload anything to a Slack channel from Gradle</sub></h1>

[📱 Demo](https://fabric.io/myupmc/android/apps/com.upmc.enterprises.myupmc.dev/beta/releases/latest) |
[📖 Documentation](#getting-started) |
[📆 Version History](https://github.com/upmc-enterprises/gradle-slack-uploader-plugin/releases)

```text
"Works to help you improve your CI and CD practices."
```

[![Supported Kotlin Versions](https://img.shields.io/badge/Kotlin-1.3.21%2B-green.svg?logo=kotlin&style=flat&logoColor=green)](https://kotlinlang.org/)
[![Supported Gradle Versions](https://img.shields.io/badge/Gradle-4.10%2B-green.svg?logo=java&style=flat&logoColor=green)](https://gradle.org/)
[![Latest Release](https://img.shields.io/github/release/upmc-enterprises/gradle-slack-uploader-plugin.svg)](https://github.com/upmc-enterprises/gradle-slack-uploader-plugin/releases)
[![Available on JitPack](https://jitpack.io/v/upmc-enterprises/gradle-slack-uploader.svg)](https://jitpack.io/#upmc-enterprises/gradle-slack-uploader)

<hr />
</div>

Sometimes it is not always practical to push new software from Git straight into production. This plugin helps minimize some of the effort required to get your software into customer's hands by pushing it to an intended, internal group over Slack.

Here are some other useful examples of how this plugin can help:

- Send Android or iOS apps to the development team's channel for uploading into the appropriate distribution store
- Upload files to a QA channel for verification and testing
- Surface artifacts from a build to better understand its performance

Whenever a file is uploaded, you can expect it to feel very familiar and flexible:

<div align="center">

![Slack message from the Gradle plugin with an APK file](docs/slack-message.png)

</div>

# Getting Started

There are two parts to installing this plugin. The first is, of course, installing it into your Gradle script and the second part is creating a Slack bot account with which the plugin uses to upload a file.

## Create the Slack Bot

Unless your Slack administrator has banned unapproved apps from being installed into your workspace, then just about anyone should be able to follow these steps.

1. Go to [api.slack.com/apps](https://api.slack.com/apps/)
1. Press the *Create New App* button. Apps within Slack enable automated bots to post messages to a Slack channel.
1. In the pop-up dialog, give the app a friendly name. This name is for your reference only and is not reflected as the bot's name in a Slack workspace whenever it posts a message. You will configure the bot's name later.

    <img alt="Slack application creation dialog" src="docs/screenshots/create-app.png" width="320" />

1. After creating the app, click on the *Bots* button, under the *Add Features and Functionality* section

    <img alt="Add bot functionality" src="docs/screenshots/add-bot-functionality.png" width="320" />

1. Click the *Add a Bot User* button and fill out the form. The Display Name is effectively the bot's First and Last Name, which appears as the sender of a Slack message. You can populate this form as you please. Here is an example set up:

    <img alt="Bot user details" src="docs/screenshots/bot-user-details.png" width="320" />

1. Save your changes and the go back to the *Basic Information* page, which is available from the left-column navigation
1. Under the *Add Features and Functionality* section, both the *Bots* and *Permissions* features should show as completed

    <img alt="Completed Features and Functionality" src="docs/screenshots/completed-features-and-functionality.png" width="320" />

1. **(Optional)** Populate the *Display Information*. This can be thought of as the bot's profile picture and status message.

    <img alt="Completed Features and Functionality" src="docs/screenshots/display-information.png" width="320" />

1. Click on *Install Your App to Your Workspace* and follow Slack's installation and permission prompts
1. After installing the app, go back to the app's configuration
1. On the left-column navigation go to *OAuth & Permissions*
1. Make a note of the *Bot User OAuth Access Token* (not the OAuth Access Token). You will need this for a subsequent step. **Keep this token safe, as having access to it enables anyone to send messages to your workspace.**

    <img alt="Bot User OAuth Access Token" src="docs/screenshots/bot-access-token.png" width="320" />

## Install the Plugin into Gradle

After adding a bot account to Slack, we now have enough information to add the plugin to Gradle.

1. Note the latest release of this plugin for use in the next step: [![Latest Release](https://img.shields.io/github/release/upmc-enterprises/gradle-slack-uploader-plugin.svg)](https://github.com/upmc-enterprises/gradle-slack-uploader-plugin/releases)
1. Open your `build.gradle` file, and add this code to load the plugin:

    ```groovy
    buildscript {
        dependencies {
            classpath 'com.github.upmc-enterprises:gradle-slack-uploader:<latest version>'
        }

        repositories {
            maven { url 'https://jitpack.io' }
        }
    }
    ```

1. Now, apply it:

    ```groovy
    apply plugin: "com.oliverspryn.gradleslackuploaderplugin"
    ```

# Configuring the Plugin

There are a few ways you can use this plugin, depending on your needs. You may wish to adapt how you use this based on your setup. Before diving into the code, let's cover what this plugin offers:

- **Configuration Block:** You must *always* configure this plugin with a block since it has required paramters:

    ```groovy
    uploadFileToSlack {
        ...
    }
    ```

    Otherwise, your build will fail with an exception until the necessary information is provided.
- **Task:** To manually kick off an upload task, run the `uploadFileToSlack` task, like so:

    ```bash
    ./gradlew uploadFileToSlack
    ```

    Keep in mind you'll probably want a few commands beforehand to run a build and generate artifacts.

## Configuration Block Options

There are several parameters which are provided to alter the behavior of the plugin. Here is an example of a fully configured block:

```groovy
uploadFileToSlack {
    comment "Our app is ready for release!"
    channels "public-release-channel", "developers"
    enabled true
    filePath "app-release.apk"
    token GRADLE_SLACK_UPLOADER_PLUGIN_TOKEN ?: "" // Defined in the global gradle.properties file
}
```

Here is an elaboration of each of these options:

| **Option** | **Default** | **Required** |                              **Description**                              |
|:----------:|:-----------:|:------------:|:-------------------------------------------------------------------------:|
|  `comment` |    *None*   |       ✅      | The comment which is shown above the file attachment in Slack             |
| `channels` |    *None*   |       ✅      | Comma separated list of channels which will receive the file              |
|  `enabled` |    `true`   |       🚫      | Whether or not to run the plugin. Useful to restrict for use only on CIs. |
| `filePath` |    *None*   |       ✅      | Path of the artifact to upload relative to the project root               |
|   `token`  |    *None*   |       ✅      | Slack bot's OAuth access token                                            |

## Technique 1 - Manually Call the Task

This approach is slightly more manual, but could provide more clarity into your build process.

1. Add the configuration block, as described in the [previous section](#configuration-block-options)
1. When running the build, call the task manually:

    ```bash
    ./gradlew build // For example
    ./gradlew uploadFileToSlack // Call this last
    ```

## Technique 2 - Attach the Plugin to the Task Graph

Modifying Gradle's task graph is very simple and allows a task to run each time a more common task finishes. For example, it's very common to run `build` to compile an application. Let's attach the plugin to that task.

1. Add the configuration block, as described in the [previous section](#configuration-block-options)
1. Add this block to run `uploadFileToSlack` after `build` finishes:

    ```groovy
    afterEvaluate {
        uploadFileToSlack.dependsOn build
    }
    ```
