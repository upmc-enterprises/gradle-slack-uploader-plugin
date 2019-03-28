<div align="center">
<img src="docs/banner.png" />

<br>
<br>

<h1 style="border-bottom: none; margin-bottom: 0; padding-bottom: 0;">Gradle Slack Uploader Plugin</h1>
<h2 style="border-bottom: none; margin-bottom: 0; padding-bottom: 0;">Upload anything to a Slack channel from Gradle</h2>

[📱 Demo](https://fabric.io/myupmc/android/apps/com.upmc.enterprises.myupmc.dev/beta/releases/latest) |
[📖 Documentation](https://upmcenterprises.atlassian.net/wiki/spaces/MyUPMCMobile) |
[📆 Version History](https://github.com/upmc-enterprises/gradle-slack-uploader-plugin/releases)

```text
"Works to help you improve your CI and CD practices."
```

<a href="https://kotlinlang.org/" target="_blank">
    <img alt="Supported Kotlin Versions" src="https://img.shields.io/badge/Kotlin-1.3.21%2B-green.svg?logo=kotlin&style=flat&logoColor=green" />
</a>

<a href="https://gradle.org/" target="_blank">
    <img alt="Supported Kotlin Versions" src="https://img.shields.io/badge/Gradle-4.10%2B-green.svg?logo=java&style=flat&logoColor=green" />
</a>

<a href="https://jitpack.io/#upmc-enterprises/gradle-slack-uploader" target="_blank">
    <img alt="Available on JitPack" src="https://jitpack.io/v/upmc-enterprises/gradle-slack-uploader.svg" />
</a>

<hr />
</div>

Sometimes it is not always practical to push new software from Git straight into production. This plugin helps minimize some of the effort required to get your software into customer's hands by making it visible to an intended, internal group just before release. Here are some other useful examples of how this plugin can help:

- Send Android or iOS apps to the development team's channel for uploading into Google Play
- Upload files to a QA channel for verification and testing
- Surface artifacts from a build to better understand its performance

Whenever a file is uploaded, you can expect it to feel very custom and look like this:

![Slack message from the Gradle plugin with an APK file](docs/slack-message.png)
