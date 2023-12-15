plugins {
	id("com.pmorozova.movies.android-library")
}

val libs by versionCatalog

android {
	configureAndroid(this)
}

dependencies {
	implementation(libs.requireLib("compose-bom"))
	implementation(libs.requireLib("material3"))
	implementation(project(":domain:navigator"))
	implementation(project(":common"))
	implementation(libs.requireLib("androidx-lifecycle-viewmodel-compose"))
	implementation(project(":domain:models"))
}