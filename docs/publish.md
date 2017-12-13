# Publish

1. Update version in `build.gradle`
1. Update version in `CHANGELOG.md`
1. Update version in `README.md`
1. Merge `develop` in `master`
1. Tag current version in format `v[version]`
1. Run `SONATYPE_USER=[username] SONATYPE_PASSWORD=[password] gradlew clean build uploadArchives`
1. Open [https://oss.sonatype.org/#stagingRepositories](https://oss.sonatype.org/#stagingRepositories)
1. Find the argon2 staging repo
1. Close the argon2 staging repo
1. Wait some time, then release the staging repo
1. Checkout `develop` branch
1. Update versions to new development version
