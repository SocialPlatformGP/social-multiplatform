import com.android.build.gradle.internal.lint.AndroidLintAnalysisTask
import com.android.build.gradle.internal.lint.LintModelWriterTask
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io/")
    }
}

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.apollo)
    alias(libs.plugins.ksp)
    alias(libs.plugins.realm)

//    alias(libs.plugins.google.services)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
//        configurations.all{
//            exclude(group = "com.github.UstadMobile.door", module = "room-annotations")
//        }
    }
    jvm()

    js {
        browser()
        binaries.executable()
    }

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenmodel)
            implementation(libs.voyager.tabnavigator)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.kodein)
            implementation(libs.composeImageLoader)
            implementation(libs.napier)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.moko.mvvm)
            implementation(libs.ktor.core)
            implementation(libs.composeIcons.featherIcons)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.multiplatformSettings)
            implementation(libs.multiplatformSettingsNoArgs)
            implementation(libs.kstore)
            implementation(libs.apollo.runtime)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.calf.file.picker)
            implementation(libs.kodein.di.framework.compose)
            implementation(libs.door.runtime)
            implementation(libs.room.annotations)

        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activityCompose)
            implementation(libs.compose.uitooling)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.ktor.client.websockets)
            implementation(libs.sqlDelight.driver.android)
            implementation(compose.preview)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.room.ktx)
            implementation(libs.androidx.paging.runtime)
            implementation("io.realm.kotlin:library-base:1.11.0")
            implementation("io.realm.kotlin:library-sync:1.11.0")
//            compileOnly("io.realm.kotlin:library-base:1.11.0")
//            configurations.all{
//                exclude(group = "com.github.UstadMobile.door", module = "room-annotations")
//            }
        }

        jvmMain.dependencies {
            implementation(compose.desktop.common)
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqlDelight.driver.sqlite)
            implementation("io.realm.kotlin:library-base:1.11.0")
            implementation("io.realm.kotlin:library-sync:1.11.0")
        }

        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(libs.ktor.client.js)
            implementation(libs.sqlDelight.driver.js)
        }

    }
}

android {
    namespace = "com.gp.socialapp"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
        targetSdk = 34

        applicationId = "com.gp.socialapp.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
        resources.srcDirs("src/commonMain/resources")

    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}


compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.gp.socialapp.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}
val version_door by extra("0.0.63o10")
val version_android_room by extra("2.6.1")
dependencies {
    add("kspJvm", "com.github.UstadMobile.door:door-compiler:$version_door")
    add("kspJs", "com.github.UstadMobile.door:door-compiler:$version_door")
    add("kspAndroid", "com.github.UstadMobile.door:door-compiler:$version_door")
    add("kspAndroid", "androidx.room:room-compiler:$version_android_room")
}

compose.experimental {
    web.application {}
}

buildConfig {
    // BuildConfig configuration here.
    // https://github.com/gmazzo/gradle-buildconfig-plugin#usage-in-kts
}

sqldelight {
    databases {
        create("MyDatabase") {
            // Database configuration here.
            // https://cashapp.github.io/sqldelight
            packageName.set("com.gp.socialapp.db")
        }
    }
}

apollo {
    service("api") {
        // GraphQL configuration here.
        // https://www.apollographql.com/docs/kotlin/advanced/plugin-configuration/
        packageName.set("com.gp.socialapp.graphql")
    }
}

//tasks.withType<LintModelWriterTask> {
//    dependsOn("copyFontsToAndroidAssets")
//}
//
//tasks.withType<AndroidLintAnalysisTask> {
//    dependsOn("copyFontsToAndroidAssets")
//}
