import org.jetbrains.compose.desktop.application.dsl.TargetFormat

allprojects {
    repositories {
        google()
        mavenCentral()
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
    alias(libs.plugins.undercouch.download)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.compose.compiler)
    id("io.realm.kotlin") version "2.0.0"

}
//ktlint {
//    android = true
//    ignoreFailures = false
//    reporters {
//        reporter(reporterType = ReporterType.PLAIN)
//        reporter(reporterType = ReporterType.CHECKSTYLE)
//        reporter(reporterType = ReporterType.SARIF)
//
//    }
//}
//
//tasks.getByPath("preBuild").dependsOn("ktlintFormat")

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
        configurations.all {
            exclude(group = "com.soywiz.korlibs.krypto", module = "krypto-android")
        }
    }
    jvm()



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
            implementation(libs.sqlDelight.coroutines)
            implementation(libs.korge.foundation)
            implementation(libs.supabase.auth)
            implementation(libs.supabase.storage)
            implementation(libs.supabase.realtime)
            implementation(libs.supabase.postgrest)
            implementation(libs.window.size.multiplatform)
            implementation(libs.kottie)
            implementation(libs.compose.placeholder.material3)
            implementation(libs.composeIcons.feather)
            implementation(libs.composeIcons.fontAwesome)
            implementation(libs.composeIcons.tablerIcons)

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
            implementation(libs.realm.base)
            implementation(libs.realm.sync)
            implementation(libs.bouquet)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.common)
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqlDelight.driver.sqlite)
            implementation(libs.realm.base)
            implementation(libs.realm.sync)
            implementation(libs.compose.pdf)
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


sqldelight {
    databases {
        create("AppDatabase") {
            // Database configuration here.
            // https://cashapp.github.io/sqldelight
            packageName.set("com.gp.socialapp.db")
        }
    }
}

