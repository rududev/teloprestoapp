plugins {
    alias(libs.plugins.android.application)
}

val gitCommitCount = ProcessBuilder("git", "rev-list", "--count", "HEAD")
    .start()
    .inputStream.bufferedReader()
    .readLine()?.trim()?.toIntOrNull() ?: 1
android {
    namespace = "com.telopresto.app"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.telopresto.app"
        minSdk = 26
        targetSdk = 36
        versionCode = gitCommitCount
        versionName = "0.02.%04d".format(gitCommitCount)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)

    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}