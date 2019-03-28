#!/bin/bash

#
# Builds the plugin, uploads it to a local repository folder and runs it against the demo project
#

../gradlew --project-dir ../ uploadArchives
../gradlew --project-dir ../ --build-file ./demo/build.gradle uploadFileToSlack
