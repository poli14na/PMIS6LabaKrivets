plugins {
	id("com.pmorozova.movies.android-library")
}

val libs by versionCatalog

android {
	configureAndroid(this)
}

dependencies {
	implementation(libs.requireLib("activity-compose"))
	implementation(libs.requireLib("compose-bom"))
	implementation(libs.requireLib("material3"))
	implementation(libs.requireLib("coil-compose"))
	implementation(project(":domain:navigator"))
	implementation(project(":arch"))
	implementation(libs.requireLib("androidx-lifecycle-viewmodel"))
	implementation(project(":common"))
	implementation(libs.requireLib("koin-android"))
	implementation(project(":domain:models"))
}