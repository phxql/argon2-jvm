# Publish

1. Create `gradle.properties`, containing:
```
signing.keyId=[ID of the GPG key]
signing.password=[Password of the GPG key]
signing.secretKeyRingFile=[Path to the keyring file]
sonatype.username=[Sonatype username]
sonatype.password=[Sonatype password]
```   
1. Update version in `build.gradle`, `CHANGELOG.md`, `README.md`
1. Create commit `Version x.x.x`   
1. Merge `develop` in `master`
1. Tag current version in format `v[version]`
1. Run `./gradlew clean build publish`
1. Open [https://oss.sonatype.org/#stagingRepositories](https://oss.sonatype.org/#stagingRepositories)
1. Find the argon2 staging repo
1. Close the argon2 staging repo
1. Wait some time, then release the staging repo
1. Push the changes: `git push` and the tags `git push --tags` 
1. Checkout `develop` branch
1. Update versions to new development version
1. Create commit `Start next development version`
1. Create a new release from the tag on [GitHub](https://github.com/phxql/argon2-jvm/tags)

## Notes:

If you can't find the secring.gpg file needed for signing, do this:

```
gpg --export-secret-keys <Key ID> > argon2.key
```

and then use the path to the `argon2.key` as `signing.secretKeyRingFile`.

If you need the short id of the key, run

```
gpg --list-secret-keys
```

The short id are the last 8 chars of the fingerprint prefixed by `0x`.
