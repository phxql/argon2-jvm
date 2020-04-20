FROM ubuntu:16.04
# Install needed tools to build (I'm unable to put gcc-arm-linux-gnueabihf with the other stuff in apt install, wtf apt?!)
RUN apt update && apt install --yes wget make binutils gcc gcc-multilib && apt install --yes gcc-arm-linux-gnueabihf gcc-aarch64-linux-gnu && apt clean

WORKDIR /
ADD build-libargon2.sh .
CMD /build-libargon2.sh
