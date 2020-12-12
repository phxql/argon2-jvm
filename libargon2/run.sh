#!/usr/bin/env bash

set -euo pipefail

DOCKER="podman" # Replace with 'docker' if you don't have podman

IMAGE_NAME="libargon2-build"
$DOCKER build -t $IMAGE_NAME -f Dockerfile context
$DOCKER run --volume $(pwd)/output:/output --rm $IMAGE_NAME

file output/linux-aarch64/libargon2.so
file output/linux-arm/libargon2.so
file output/linux-x86/libargon2.so
file output/linux-x86-64/libargon2.so
