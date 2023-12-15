plugins {
	id("com.pmorozova.movies.android-library")
}

val libs by versionCatalog

android {
	configureAndroid(this)
}

dependencies {
	implementation(libs.requireLib("compose-runtime"))
	implementation(libs.requireLib("ui-graphics"))
	implementation(libs.requireLib("coil-compose"))
	implementation(libs.requireLib("material3"))
}