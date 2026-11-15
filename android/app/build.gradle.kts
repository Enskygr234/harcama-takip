plugins {
    id("com.android.application")
}

// Web uygulamasını (depo kökündeki index.html ve ikonlar) APK'nın içine kopyala.
val webDir = layout.buildDirectory.dir("generated/web")
val copyWeb by tasks.registering(Copy::class) {
    from(rootProject.projectDir.parentFile) {
        include("index.html", "icons/**")
    }
    into(webDir)
}

android {
    namespace = "com.enskygr234.harcamatakip"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.enskygr234.harcamatakip"
        minSdk = 24
        targetSdk = 34
        versionCode = (System.getenv("VERSION_CODE") ?: "1").toInt()
        versionName = System.getenv("VERSION_NAME") ?: "1.0.0"
    }

    // Bu anahtar bilerek depoda: GitHub'da gizli anahtar tanımlanamadığı için
    // her sürüm aynı anahtarla imzalanır ve eski kurulumun üstüne güncellenir.
    signingConfigs {
        create("release") {
            storeFile = file("harcama-takip.jks")
            storePassword = "harcamatakip"
            keyAlias = "harcama"
            keyPassword = "harcamatakip"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    sourceSets["main"].assets.srcDir(webDir.get().asFile)

    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }
}

tasks.named("preBuild") {
    dependsOn(copyWeb)
}

dependencies {
    implementation("androidx.webkit:webkit:1.12.1")
}
