import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.21"
    id("com.github.ben-manes.versions")
    id("com.google.devtools.ksp") version "2.2.21-2.0.4"
    id("com.anthonycr.plugins.mezzanine") version "2.2.0"
    id("com.google.gms.google-services")
    id("com.apollographql.apollo") version "4.4.1"
}

android {
    compileSdk = 36

    defaultConfig {
        minSdk = 26
        targetSdk = 35
        versionName = "0.1.0"
        vectorDrawables.useSupportLibrary = true
    }

    val isCi = System.getenv("CI") == "true"

    sourceSets {
        create("lightningPlus").apply {
            setRoot("src/LightningPlus")
        }
        /*if (!isCi) {
            create("lightningLite").apply {
                setRoot("src/LightningLite")
            }
        }*/
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    buildTypes {
        named("debug") {
            multiDexEnabled = true
            isMinifyEnabled = false
            isShrinkResources = false
            setProguardFiles(listOf("proguard-project.txt"))
            enableUnitTestCoverage = false
            enableAndroidTestCoverage = false
        }

        named("release") {
            multiDexEnabled = false
            isMinifyEnabled = !isCi
            isShrinkResources = !isCi
            setProguardFiles(listOf("proguard-project.txt"))
            enableUnitTestCoverage = false
            enableAndroidTestCoverage = false

            ndk {
                abiFilters.add("arm64-v8a")
                abiFilters.add("armeabi-v7a")
                abiFilters.add("armeabi")
                abiFilters.add("mips")
            }
        }
    }

    flavorDimensions.add("capabilities")

    productFlavors {
        create("lightningPlus") {
            dimension = "capabilities"
            buildConfigField("boolean", "FULL_VERSION", "Boolean.parseBoolean(\"true\")")
            applicationId = "com.veon.frisbee"
            versionCode = 1
        }

/*        if (!isCi) {
            create("lightningLite") {
                dimension = "capabilities"
                buildConfigField("boolean", "FULL_VERSION", "Boolean.parseBoolean(\"false\")")
                applicationId = "com.veon.frisbee.lite"
                versionCode = 102
            }
        }*/
    }
    packaging {
        resources {
            excludes += listOf(".readme")
        }
    }
    lint {
        abortOnError = true
    }
    namespace = "acr.browser.lightning"
}

dependencies {
    // multidex debug
    debugImplementation("androidx.multidex:multidex:2.0.1")

    // test dependencies
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.assertj:assertj-core:3.27.6")
    testImplementation("org.mockito:mockito-core:5.21.0")
    testImplementation("com.nhaarman:mockito-kotlin:1.6.0") {
        exclude(group = "org.jetbrains.kotlin")
    }
    testImplementation("org.robolectric:robolectric:4.16")

    // Room database
    val roomVersion = "2.7.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")

    // support libraries
    implementation("androidx.palette:palette-ktx:1.0.0")
    implementation("androidx.annotation:annotation:1.9.1")
    implementation("androidx.vectordrawable:vectordrawable-animated:1.2.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.fragment:fragment-ktx:1.8.9")
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("androidx.preference:preference-ktx:1.2.1")
    implementation("androidx.webkit:webkit:1.14.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    // html parsing for reading mode
    implementation("org.jsoup:jsoup:1.21.2")

    // file reading
    val mezzanineVersion = "2.2.0"
    implementation("com.anthonycr.mezzanine:core:$mezzanineVersion")
    ksp("com.anthonycr.mezzanine:processor:$mezzanineVersion")

    // dependency injection
    val daggerVersion = "2.57.2"
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")
    compileOnly("javax.annotation:jsr250-api:1.0")

    // permissions
    implementation("com.guolindev.permissionx:permissionx:1.8.1")

    implementation("com.squareup.okhttp3:okhttp:5.3.2")

    implementation("io.coil-kt.coil3:coil:3.3.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.3.0")

    // Apollo GraphQL
    implementation("com.apollographql.apollo:apollo-runtime:4.4.1")

    // rx
    implementation("io.reactivex.rxjava3:rxjava:3.1.12")
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2")
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")

    // memory leak analysis
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.14")

    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.2.21")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    implementation("com.github.veonadtech.prebid-android-sdk:mobile:0.1.1")
    implementation("com.github.veonadtech.prebid-android-sdk:core:0.1.1")
    implementation("com.github.veonadtech.prebid-android-sdk:eventhandlers:0.1.1")
    implementation("com.github.veonadtech.prebid-android-sdk:prebidorg:0.1.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.8.0"))
    implementation("com.google.firebase:firebase-analytics")

    // Jetpack Compose
    val composeBom = platform("androidx.compose:compose-bom:2026.01.01")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

mezzanine {
    files = files(
        "src/main/html/list.html",
        "src/main/html/bookmarks.html",
        "src/main/html/homepage.html",
        "src/main/js/InvertPage.js",
        "src/main/js/TextReflow.js",
        "src/main/js/ThemeColor.js"
    )
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

kotlin {
    jvmToolchain(17)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        languageVersion.set(KotlinVersion.KOTLIN_2_1)
    }
}

java {
    targetCompatibility = JavaVersion.VERSION_17
    sourceCompatibility = JavaVersion.VERSION_17
}

apollo {
    service("api") {
        packageName.set("acr.browser.lightning.graphql")
        schemaFiles.from("src/main/graphql/schema.graphqls")
        srcDir("src/main/graphql")
    }
}
