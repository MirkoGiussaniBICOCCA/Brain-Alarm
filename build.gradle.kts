// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
    }
    dependencies {
        val navVersion = "2.7.5"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.6")
    }
}

plugins {
    id("com.android.application") version "8.1.2" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false


}