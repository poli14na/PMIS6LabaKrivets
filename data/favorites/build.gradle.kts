plugins {
	id("com.pmorozova.movies.android-library")
}

val libs by versionCatalog

dependencies {
	implementation(libs.requireLib("kotlin-coroutines-core"))
	implementation(project(":feature:favorites"))
	implementation(project(":common"))
	implementation(project(":domain:models"))
}