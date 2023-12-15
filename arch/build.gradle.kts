plugins {
	id("com.pmorozova.movies.android-library")
}

val libs by versionCatalog

android {
	configureAndroid(this)
}

dependencies {
	implementation(libs.requireLib("androidx-lifecycle-runtime-compose"))
	implementation(libs.requireLib("androidx-lifecycle-viewmodel"))
	implementation(libs.requireLib("koin-android-compat"))
	implementation(libs.requireLib("lifecycle-runtime-ktx"))
	implementation(libs.requireLib("androidx-lifecycle-viewmodel-compose"))
}