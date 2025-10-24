#!/usr/bin/env bash
set -euo pipefail
BASE_DIR="$(cd "$(dirname "$0")"; pwd)"
WRAPPER_DIR="$BASE_DIR/.mvn/wrapper"
WRAPPER_JAR="$WRAPPER_DIR/maven-wrapper.jar"
PROPS_FILE="$WRAPPER_DIR/maven-wrapper.properties"
mkdir -p "$WRAPPER_DIR"
WRAPPER_URL="$(awk -F= '/^wrapperUrl=/{print $2}' "$PROPS_FILE" 2>/dev/null || true)"
if [ -z "${WRAPPER_URL:-}" ]; then
  echo "ERROR: wrapperUrl not found in $PROPS_FILE"
  exit 1
fi
if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Downloading Maven Wrapper from $WRAPPER_URL"
  curl -fsSL "$WRAPPER_URL" -o "$WRAPPER_JAR"
fi
exec java ${MAVEN_OPTS:-} -classpath "$WRAPPER_JAR" org.apache.maven.wrapper.MavenWrapperMain "$@"
