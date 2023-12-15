plugins {
	id("com.pmorozova.movies.android-library")
}

val libs by versionCatalog

dependencies {
	implementation(project(":feature:movie"))
	implementation(project(":common"))
	implementation(project(":feature:favorites"))
	implementation(project(":domain:models"))
}