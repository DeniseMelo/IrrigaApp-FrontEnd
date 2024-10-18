val openWeatherMapApiKey: String by project
val mapboxDownloadsToken: String by project

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "br.com.fiap.irrigaapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.fiap.irrigaapp"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "OPENWEATHERMAPAPIKEY", "\"$openWeatherMapApiKey\"")
        buildConfigField("String", "MAPBOXDOWNLOADSTOKEN", "\"$mapboxDownloadsToken\"")
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
        buildConfig = true // Habilitar a geração de BuildConfig
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

dependencies {
    // Retrofit para comunicação com APIs
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // Coroutines para operações assíncronas
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Room para persistência local
    implementation(libs.room.runtime)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.androidx.runtime.livedata)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.ktx)

    // DataStore para salvar preferências
    implementation(libs.datastore.preferences)
    implementation(libs.material.icons.extended)

    // Material Design
    implementation(libs.material)

    implementation(libs.navigation.compose)

    implementation(libs.mapbox.android)
    implementation(libs.maps.compose)

    // Outras dependências já existentes
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Testes
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}
