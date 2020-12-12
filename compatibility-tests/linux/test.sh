#!/usr/bin/env bash

set -euo pipefail

if [ ! -f context/compatibility-tests.tar ]; then
    echo "Run './gradlew clean build' and copy the tar file from build/distributions/ to the context/ directory"
    exit 1
fi

DOCKER="podman" # Replace with 'docker' if you don't have podman
DOCKERFILE="ubuntu-20.10" # See the dockerfiles/ folder and set this variable to the Dockerfile you want to test

IMAGE_NAME="argon2-$DOCKERFILE"
$DOCKER build -t $IMAGE_NAME -f dockerfiles/$DOCKERFILE context
$DOCKER run --rm $IMAGE_NAME
