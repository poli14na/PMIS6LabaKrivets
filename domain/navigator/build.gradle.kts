plugins {
	id("com.pmorozova.movies.android-library")
}

val libs by versionCatalog

dependencies {
	implementation(libs.requireLib("compose-runtime"))
}