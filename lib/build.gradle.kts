plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "tech.qingge.lib.astv"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

}

publishing {
    publications {
        //noinspection WrongGradleMethod
        register<MavenPublication>("release") {
            groupId = "tech.qingge"
            artifactId = "AutoScaleTextView"
            version = "1.3"
            afterEvaluate {
                from(components["release"])
            }
        }
    }
}