# Argon2 compatibility

All these tests have been done with the standard Argon2-JVM library (with the embedded libargon2 C library).

Tested version of Argon2-JVM: **2.7**

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
