// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply true
    alias(libs.plugins.google.gms.google.services) apply true
}
android {
    namespace = "com.example.application_1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.application_1"
        minSdk = 35
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        isCoreLibraryDesugaringEnabled = true
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
    implementation(platform(libs.firebase.bom))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.analytics)
    implementation(libs.play.services.tasks)
    implementation(libs.firebase.auth)
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.exp4j)
    implementation(libs.jsoup)
    implementation(libs.material)
    // Firebase / Google Play Services 样例依赖
    implementation(libs.play.services.auth)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.tasks)
    implementation(libs.play.services.basement)

    // 核心库反糖的实现库（一定要放在 implementation 之后）
    coreLibraryDesugaring(libs.desugar.jdk.libs)
}