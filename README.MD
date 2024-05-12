# 🎓A Social Platform for Student Communication with AI-Powered Content Moderation

**Welcome to the repository of our cross-platform application (Android, Desktop, and Web) for college students!** This comprehensive platform is designed to facilitate focused learning, efficient communication, and task management. Our goal is to save students time, minimize distractions, and enhance their overall academic experience.

## 🌟 Core Features

- **Chat and Group Chat:** Facilitates communication among students and between students and professors.
- **Academic Social Posts:** Allows students to share academic-related content and engage in educational discussions.
- **Study Material Repository:** A centralized place for sharing and accessing study materials.
- **Announcements Channel:** A dedicated channel for important academic announcements.
- **Task and Deadline Management:** Helps students manage their academic tasks and deadlines.
- **Grade Tracking:** Allows students to keep track of their academic progress.
- **AI-Powered Content Moderation:** Ensures that discussions remain focused on educational topics and maintain a respectful, supportive environment.

## 🚀 Unique Selling Points

1. **Integrated Ecosystem:** An all-in-one solution, combining communication, content sharing, academic resource management, and task management in a single platform.
2. **AI Content Moderation:** The application employs AI-driven content moderation to ensure that discussions remain focused on educational topics and maintain a respectful, supportive environment.
3. **Customizable Professor Engagement:** The application's flexibility in professor visibility and interaction empowers educators to manage their availability effectively, responding to student inquiries when they have the time, thus enhancing the teaching experience.

## 📚 Libraries Used

| Library | Version | Functionality |
| --- | --- | --- |
| calfFilePicker | 0.4.0 | A library for picking files. |
| korgeFoundation | 5.1.0 | A foundation library for the Korge game engine. |
| supabase-client | 2.4.0 | A Kotlin client for interacting with Supabase. |
| kodeinDiFrameworkCompose | 7.21.1 | A dependency injection framework for Jetpack Compose. |
| kotlin | 1.9.22 | The Kotlin standard library. |
| compose | 1.6.0 | The Jetpack Compose UI toolkit. |
| agp | 8.2.2 | The Android Gradle plugin. |
| androidx-appcompat | 1.6.1 | A library providing backward-compatible versions of Android components. |
| androidx-activityCompose | 1.8.2 | A library for building Compose UIs within an Activity. |
| compose-uitooling | 1.6.1 | Tools for building UIs with Jetpack Compose. |
| realm | 1.13.0 | A mobile database that runs directly inside phones, tablets or wearables. |
| voyager | 1.1.0-alpha03 | A navigation library for Jetpack Compose. |
| composeImageLoader | 1.7.1 | An image loading library for Jetpack Compose. |
| napier | 2.7.0 | A multiplatform logging library. |
| buildConfig | 4.1.1 | A plugin for generating a BuildConfig class. |
| kotlinx-coroutines | 1.7.3 | A library for writing asynchronous code using coroutines. |
| moko-mvvm | 0.16.1 | A Model-View-ViewModel library for Kotlin Multiplatform. |
| ktor-client | 2.3.10 | A Kotlin client for making HTTP requests. |
| composeIcons | 1.1.0 | A library for using icons in Jetpack Compose. |
| kotlinx-serialization | 1.6.2 | A library for serializing objects to and from JSON. |
| kotlinx-datetime | 0.5.0 | A library for working with dates and times. |
| multiplatformSettings | 1.1.1 | A library for working with settings on multiple platforms. |
| kstore | 0.7.1 | A library for managing state in Kotlin. |
| sqlDelight | 2.0.1 | A library for generating Kotlin APIs from SQL statements. |
| apollo | 4.0.0-beta.4 | A GraphQL client. |
| dotsIndicator | 5.0 | A library for showing dots as an indicator. |
| undercouch-download | 5.6.0 | A plugin for downloading files. |
| ktlint | 11.0.0 | A linter for Kotlin. |
| bouquet | 1.1.2 | A library for managing state in Jetpack Compose. |
| compose-pdf | 1.1.1 | A library for creating PDFs with Jetpack Compose. |

## 📸 Screenshots

<details>
<summary>Click to expand</summary>

### Chat Screen

![Chat Screen](https://firebasestorage.googleapis.com/v0/b/social-platform-3e56e.appspot.com/o/Picture2.png?alt=media&token=adb251eb-816d-4c12-8165-994ac57164ce)

### Academic Social Posts Screen

![Academic Social Posts Screen](https://firebasestorage.googleapis.com/v0/b/social-platform-3e56e.appspot.com/o/Picture1.png?alt=media&token=523f4e8e-0719-4884-b1bf-07cda4bbbe93)

### Study Material Repository Screen

![Study Material Repository Screen](https://firebasestorage.googleapis.com/v0/b/social-platform-3e56e.appspot.com/o/Picture3.png?alt=media&token=4d82f399-edc4-4716-90aa-596fb64de03b)

### Announcements Channel Screen
 Under construction

### Task and Deadline Management Screen
 Under construction
### Grade Tracking Screen
 Under construction

</details>

## 🎥 Demo

![Video](https://firebasestorage.googleapis.com/v0/b/social-platform-3e56e.appspot.com/o/Media1.mp4?alt=media&token=d8e38df0-98b4-4400-aa32-7ad9962a73d3)

## 📝 Contributing

Contributions are welcome. 

## Before running!
 - install JDK 17 on your machine
 - add `local.properties` file to the project root and set a path to Android SDK there

### Android
To run the application on android device/emulator:  
 - open project in Android Studio and run imported android run configuration

To build the application bundle:
 - run `./gradlew :composeApp:assembleDebug`
 - find `.apk` file in `composeApp/build/outputs/apk/debug/composeApp-debug.apk`
 - 
### Desktop
Run the desktop application: `./gradlew :composeApp:run`

### Browser
Run the browser application: `./gradlew :composeApp:jsBrowserDevelopmentRun`
