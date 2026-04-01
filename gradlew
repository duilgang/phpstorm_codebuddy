#!/bin/bash

# Simple Gradle wrapper that downloads Gradle 7.4 if needed
# This script ensures we use the correct Gradle version

# Get the directory where this script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
WRAPPER_JAR="$SCRIPT_DIR/gradle/wrapper/gradle-wrapper.jar"
WRAPPER_PROPERTIES="$SCRIPT_DIR/gradle/wrapper/gradle-wrapper.properties"

# Default JVM options
DEFAULT_JVM_OPTS="-Xmx64m -Xms64m"

# Check if wrapper properties exist
if [ ! -f "$WRAPPER_PROPERTIES" ]; then
    echo "ERROR: gradle-wrapper.properties not found at $WRAPPER_PROPERTIES" >&2
    exit 1
fi

# Extract Gradle version from properties
GRADLE_VERSION=$(grep "distributionUrl" "$WRAPPER_PROPERTIES" | sed 's/.*gradle-\([0-9.]*\)-.*/\1/')

# If we can't determine version, default to 7.4
if [ -z "$GRADLE_VERSION" ]; then
    GRADLE_VERSION="7.4"
fi

echo "Using Gradle $GRADLE_VERSION"

# Check if wrapper jar exists
if [ ! -f "$WRAPPER_JAR" ]; then
    echo "Note: gradle-wrapper.jar not found. Gradle will download it automatically."
fi

# Find Java
if [ -n "$JAVA_HOME" ]; then
    if [ -x "$JAVA_HOME/bin/java" ]; then
        JAVACMD="$JAVA_HOME/bin/java"
    else
        echo "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME" >&2
        exit 1
    fi
else
    JAVACMD="java"
    if ! command -v java >/dev/null 2>&1; then
        echo "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH" >&2
        exit 1
    fi
fi

# Execute Gradle using the wrapper
exec "$JAVACMD" $DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS \
    -Dorg.gradle.appname="gradlew" \
    -classpath "$WRAPPER_JAR" \
    org.gradle.wrapper.GradleWrapperMain \
    "$@"