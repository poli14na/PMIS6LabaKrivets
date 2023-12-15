// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
//	kotlin("android")
	id(libs.plugins.com.android.application.get().pluginId) apply false
	id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId) apply false
	alias(libs.plugins.serialization) apply false
	alias(libs.plugins.ksp) apply false
	id(libs.plugins.parcelize.get().pluginId) apply false
	id("org.jetbrains.kotlin.jvm") apply false
}
true // Needed to make the Suppress annotation work for the plugins block

buildscript {
	repositories {
		google()
		mavenCentral()
	}

	dependencies {
		classpath(libs.android.gradle)
		classpath(libs.kotlin.gradle)
	}
}

allprojects {
	tasks {
		withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
			compilerOptions {
				jvmTarget.set(Config.jvmTarget)
				languageVersion.set(Config.kotlinLanguageVersion)
			}
		}
	}
}