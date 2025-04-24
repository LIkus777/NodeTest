plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.zaur.nodetest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.zaur.nodetest"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core & AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)                       // appcompat
    implementation(libs.androidx.constraintlayout)               // constraintlayout
    implementation(libs.androidx.cardview)                       // cardview
    implementation(libs.androidx.recyclerview)                   // recyclerview

    // Lifecycle & Navigation
    implementation(libs.androidx.lifecycle.runtime.ktx)          // lifecycleRuntimeKtx
    implementation(libs.androidx.compose.lifecycle.runtime)      // lifecycle-runtime-compose
    implementation(libs.navigation)                              // navigation-compose

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)               // activityCompose
    implementation(libs.androidx.ui)                             // compose ui
    implementation(libs.androidx.ui.graphics)                    // ui-graphics
    implementation(libs.androidx.ui.tooling.preview)             // ui-tooling-preview
    implementation(libs.androidx.material3)                      // material3

    // Room
    implementation(libs.androidx.room.runtime)                   // room-runtime
    implementation(libs.androidx.room.ktx)                       // room-ktx
    //ksp(libs.androidx.room.compiler)                            // room-compiler

    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)               // workManager

    // DataStore
    implementation(libs.androidx.datastore.preferences)          // datastore-preferences
    implementation(libs.androidx.datastore.core)                 // datastore-core

    // Serialization & Gson
    implementation(libs.kotlinx.serialization.json)              // serializationJson
    implementation(libs.gson)                                    // gson

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)                 // coroutines

    // Testing
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    testImplementation("androidx.test:core-ktx:1.5.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // add this
    androidTestImplementation("androidx.test:core-ktx:1.5.0") //add this
    testImplementation(libs.junit)
    testImplementation(libs.junit.junit)                               // junit
    androidTestImplementation(libs.androidx.junit)               // androidx-junit
    androidTestImplementation(libs.androidx.espresso.core)       // espresso-core

    // Compose Testing
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)      // ui-test-junit4
    debugImplementation(libs.androidx.ui.tooling)                // ui-tooling
    debugImplementation(libs.androidx.ui.test.manifest)          // ui-test-manifest
    testImplementation(kotlin("test"))
}