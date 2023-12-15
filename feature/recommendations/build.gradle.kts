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
	implementation(libs.requireLib("androidx-lifecycle-viewmodel-compose"))
	implementation(project(":common"))
	implementation(libs.requireLib("koin-androidx-compose"))
	implementation(libs.requireLib("koin-compose"))
	implementation(project(":domain:models"))
}