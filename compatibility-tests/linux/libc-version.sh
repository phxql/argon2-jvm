#!/usr/bin/env bash

set -euo pipefail

DOCKER="podman" # Replace with 'docker' if you don't have podman
DOCKERFILE="ubuntu-20.10" # See the libc-version/ folder and set this variable to the Dockerfile you want to test

IMAGE_NAME="argon2-libc-$DOCKERFILE"
$DOCKER build -t $IMAGE_NAME -f libc-version/$DOCKERFILE context
$DOCKER run --rm $IMAGE_NAME
