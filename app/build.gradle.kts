plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.crudmahasiswa"
    compileSdk = 35   // sesuai AGP 8.7.3, maksimal SDK 35
//    compileSdk {
//        version = release(35) {
//            minorApiLevel = 1
//        }
//    }

    defaultConfig {
        applicationId = "com.example.crudmahasiswa"
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
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Room Database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)       // untuk suspend/coroutines
    kapt(libs.room.compiler)            // annotation processor — membaca @Entity, @Dao, @Database

    // lifecycleScope untuk menjalankan coroutine di Activity
    implementation(libs.lifecycle.runtime)

    // Tambahkan dua baris ini:
    implementation("androidx.recyclerview:recyclerview:1.3.2")
//    implementation("com.google.android.material:material:1.13.0")
}