# Android App Contribution Guidelines

Before submitting a pull request, please ensure that you run the following Gradle tasks to maintain code quality:

- **ktlintFormat:** Formats your Kotlin code according to our coding standards.
- **detekt:** Runs static analysis to detect potential code issues.

You can run both tasks with the following command from the `android-app`.

```bash
./gradlew ktlintFormat detekt
```
