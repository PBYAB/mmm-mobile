plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.example.mmm_mobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mmm_mobile"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

val glideVersion = "4.12.0"

// Glide - Biblioteka do ładowania i wyświetlania obrazów
dependencies {
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    annotationProcessor("com.github.bumptech.glide:compiler:$glideVersion")
}

val accompanistVersion = "0.20.2"

// Accompanist - Dodatkowe narzędzia dla Jetpack Compose
dependencies {
    implementation("com.google.accompanist:accompanist-pager:$accompanistVersion")
}

val roomVersion = "2.6.1"

// Room - Biblioteka do obsługi bazy danych
dependencies {
    implementation("androidx.room:room-runtime:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    androidTestImplementation("androidx.room:room-testing:$roomVersion")
}

val composeVersion = "2023.08.00"
val runtimeLiveDataVersion = "1.5.4"
val lifecycleExtensionsVersion = "2.2.0"
val pagingVersion = "3.2.1"
val lifecycleCommonJava8Version = "2.6.2"
val navigationComposeVersion = "2.7.6"
val coilComposeVersion = "2.5.0"
val composeUiVersion = "1.5.4"
val composeMaterialVersion = "1.5.4"
val cameraCoreVersion = "1.3.1"

// Jetpack Compose i powiązane biblioteki
dependencies {
    implementation("androidx.compose.runtime:runtime-livedata:$runtimeLiveDataVersion")
    implementation("androidx.lifecycle:lifecycle-extensions:$lifecycleExtensionsVersion")
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
    implementation("androidx.paging:paging-compose:$pagingVersion")
    kapt("androidx.lifecycle:lifecycle-common-java8:$lifecycleCommonJava8Version")
    implementation("androidx.navigation:navigation-compose:$navigationComposeVersion")
    implementation("io.coil-kt:coil-compose:$coilComposeVersion")
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.material:material:$composeMaterialVersion")
    implementation("androidx.camera:camera-core:$cameraCoreVersion")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(platform("androidx.compose:compose-bom:$composeVersion"))
    implementation(platform("androidx.compose:compose-bom:$composeVersion"))
    implementation(platform("androidx.compose:compose-bom:$composeVersion"))
    implementation(platform("androidx.compose:compose-bom:$composeVersion"))
    implementation(platform("androidx.compose:compose-bom:$composeVersion"))
    implementation(platform("androidx.compose:compose-bom:$composeVersion"))
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeVersion"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeVersion"))
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeVersion"))
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeVersion"))
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeVersion"))
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeVersion"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

val cameraxVersion = "1.4.0-alpha03"

// CameraX - Biblioteka do obsługi aparatu
dependencies {
    implementation("androidx.camera:camera-camera2:$cameraxVersion")
    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
    implementation("androidx.camera:camera-view:$cameraxVersion")
}


val accompanistPermissionsVersion = "0.33.2-alpha"
// Accompanist Permissions - Narzędzia do obsługi uprawnień
dependencies {
    implementation("com.google.accompanist:accompanist-permissions:$accompanistPermissionsVersion")
}

val kotlinStdlibJdk8Version = "1.9.10"
val kotlinReflectVersion = "1.8.22"
val moshiKotlinVersion = "1.14.0"
val moshiAdaptersVersion = "1.14.0"

// Kotlin i Moshi - Obsługa języka Kotlin i konwersji JSON
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinStdlibJdk8Version")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinReflectVersion")
    implementation("com.squareup.moshi:moshi-kotlin:$moshiKotlinVersion")
    implementation("com.squareup.moshi:moshi-adapters:$moshiAdaptersVersion")
}

val retrofitVersion = "2.9.0"
val converterGsonVersion = "2.9.0"
val okhttpVersion = "4.12.0"

// Retrofit - Biblioteka do komunikacji sieciowej
dependencies {
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$converterGsonVersion")
    implementation("com.squareup.okhttp3:okhttp:$okhttpVersion")
}

val barcodeScanningVersion = "17.2.0"
// Google ML Kit - Biblioteka do przetwarzania obrazów
dependencies {
    implementation ("com.google.mlkit:barcode-scanning:$barcodeScanningVersion")
}

val lifecycleRuntimeKtxVersion = "2.6.2"
val activityComposeVersion = "1.8.2"
val junitVersion = "4.13.2"
val testExtJUnitVersion = "1.1.5"
val espressoCoreVersion = "3.5.1"
val kotlintestRunnerJUnit5Version = "3.4.2"

// Inne biblioteki ogólne
dependencies {
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleRuntimeKtxVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$testExtJUnitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoCoreVersion")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:$kotlintestRunnerJUnit5Version")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

val workVersion = "2.9.0"

//WorkManager
dependencies {
    implementation("androidx.work:work-runtime-ktx:$workVersion")
}