plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.milad.dall_e.desktop"
version = "1.0.0"

kotlin {
    jvm {
        withJava()
        /*compilations.all {
            kotlinOptions.jvmTarget = "17"
        }*/
    }
    sourceSets {
        val jvmMain by getting {
            kotlin.srcDirs("src/jvmMain/kotlin")
            dependencies {
                implementation(compose.desktop.currentOs)
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.ui)
                api(compose.materialIconsExtended)

                implementation(project(":shared"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}
