# Argon2 compatibility

All these tests have been done with the standard Argon2-JVM library (with the embedded libargon2 C library).

Tested version of Argon2-JVM: **2.8**

| Distribution | Works | libc |
|--------------|-------|------|
| CentOS 6     | No    | 2.12 |
| CentOS 7     | Yes   | 2.17 |
| CentOS 8     | Yes   | 2.28 |
| Debian 8     | Yes   | 2.19 |
| Debian 9     | Yes   | 2.24 |
| Debian 10    | Yes   | 2.28 |
| Ubuntu 14.04 | Yes   | 2.19 |
| Ubuntu 16.04 | Yes   | 2.23 |
| Ubuntu 18.04 | Yes   | 2.27 |
| Ubuntu 19.10 | Yes   | 2.30 |
| Ubuntu 20.04 | Yes   | 2.31 |
| Ubuntu 20.10 | Yes   | 2.32 |
| Alpine 3     | No    | -    |

## Workarounds

### CentOS 6

The embedded libargon2 fails with 

```
Exception in thread "main" java.lang.UnsatisfiedLinkError: /lib64/libc.so.6: version `GLIBC_2.14' not found (required by /root/.cache/JNA/temp/jna1823458227177381652.tmp)
```

You need to compile argon2 by yourself and install it on your system:

```
cd /tmp
yum install wget gcc make
wget https://github.com/P-H-C/phc-winner-argon2/archive/20190702.tar.gz
tar xzf 20190702.tar.gz
cd phc-winner-argon2-20190702/
make
make install
```

### Alpine 3

The embedded libargon2 fails with

```
#
# A fatal error has been detected by the Java Runtime Environment:
#
#  SIGSEGV (0xb) at pc=0x0000000000000eb6, pid=1, tid=14
#
# JRE version: OpenJDK Runtime Environment (11.0.7+11) (build 11.0.7+11-alpine-r1)
# Java VM: OpenJDK 64-Bit Server VM (11.0.7+11-alpine-r1, mixed mode, tiered, compressed oops, g1 gc, linux-amd64)
# Problematic frame:
# C  0x0000000000000eb6
#
# Core dump will be written. Default location: Core dumps may be processed with "/usr/lib/systemd/systemd-coredump %P %u %g %s %t %c %h" (or dumping to //core.1)
#
# An error report file with more information is saved as:
# //hs_err_pid1.log
#
# If you would like to submit a bug report, please visit:
#   https://gitlab.alpinelinux.org/alpine/aports/issues
# The crash happened outside the Java Virtual Machine in native code.
# See problematic frame for where to report the bug.
#
```

Alpine uses the musl C lib instead of the glibc. The solution is to install libargon2 via the package manager:

```
apk add argon2-dev
```