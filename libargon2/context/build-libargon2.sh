#!/usr/bin/env bash

set -euo pipefail

# Create output folders
mkdir -p /output/{linux-aarch64,linux-arm,linux-x86,linux-x86-64}

ARGON2_VERSION="20190702"

# Download libargon2 source
cd /tmp
wget https://github.com/P-H-C/phc-winner-argon2/archive/$ARGON2_VERSION.tar.gz
tar xzf $ARGON2_VERSION.tar.gz
cd phc-winner-argon2-$ARGON2_VERSION

# Compile for x86
make clean && CFLAGS=-m32 OPTTARGET=generic make
cp libargon2.so.1 /output/linux-x86/libargon2.so

# Compile for x64
make clean && CFLAGS=-m64 OPTTARGET=generic make
cp libargon2.so.1 /output/linux-x86-64/libargon2.so

# Compile for ARM
make clean && CC=arm-linux-gnueabihf-gcc make
cp libargon2.so.1 /output/linux-arm/libargon2.so

# Compile for ARM-64
make clean && CC=aarch64-linux-gnu-gcc make
cp libargon2.so.1 /output/linux-aarch64/libargon2.so
