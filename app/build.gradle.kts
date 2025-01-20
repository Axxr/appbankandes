plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.aplicationbank"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.aplicationbank"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "BASE_URL", "\"https://fxservicesstaging.nunchee.com/api/1.0/\"")
        buildConfigField("String", "AUTH_HEADER", "\"Basic cHJ1ZWJhc2RldjpwcnVlYmFzZGV2U2VjcmV0\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packaging {
        resources {
            pickFirsts += "/META-INF/LICENSE.md"
            pickFirsts += "/META-INF/LICENSE-notice.md"
            // O alternativamente usar excludes:
            // resources.excludes.add("/META-INF/LICENSE.md")
            // resources.excludes.add("/META-INF/LICENSE-notice.md")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // Retrofit + Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Security
    implementation(libs.androidx.security.crypto)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // DateTime
    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.navigation.compose)

    implementation (libs.google.accompanist.swiperefresh)

    //icons
    implementation (libs.androidx.material.icons.extended)

    //Svg
    implementation (libs.coil.compose)
    implementation (libs.coil.svg)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    //test
    androidTestImplementation (libs.junit)
    androidTestImplementation (libs.mockito.core)
    androidTestImplementation (libs.kotlinx.coroutines.test)
    androidTestImplementation (libs.mockito.junit.jupiter)
    androidTestImplementation (libs.androidx.junit.v115)
    androidTestImplementation (libs.androidx.espresso.core.v351)

    testImplementation (libs.junit)
    testImplementation (libs.mockito.core)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.mockito.junit.jupiter)
    testImplementation (libs.mockito.inline)

    // Dependencia para JUnit 5
    testImplementation (libs.junit.jupiter.api)
    testRuntimeOnly (libs.junit.jupiter.engine)

    // Dependencia para usar Mockito con JUnit 5
    testImplementation (libs.mockito.junit.jupiter)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


}