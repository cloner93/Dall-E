plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.1.0-alpha08").apply(false)
    id("com.android.library").version("8.1.0-alpha08").apply(false)
    id("org.jetbrains.compose").version("1.3.1") apply false
    id("com.codingfeline.buildkonfig").version("0.13.3") apply false
    kotlin("android").version("1.8.0").apply(false)
    kotlin("multiplatform").version("1.8.0").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
