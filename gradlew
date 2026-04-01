#!/bin/sh

# Try to use system gradle if available
if command -v gradle >/dev/null 2>&1; then
    exec gradle "$@"
fi

# Otherwise use the Gradle that GitHub Actions setup
exec /usr/bin/gradle "$@"