plugins {
	id("com.pmorozova.movies.android-library")
	id("org.jetbrains.kotlin.plugin.parcelize")
}

val libs by versionCatalog

android {
	namespace = Config.applicationId + ".impl.navigation"
	configureAndroid(this)
}

dependencies {
	implementation(libs.requireLib("compose-bom"))
	implementation(libs.requireLib("material3"))
	implementation(libs.requireLib("reimagined"))
	implementation(project(":domain:navigator"))
	implementation(project(":feature:favorites"))
	implementation(project(":feature:search"))
	implementation(project(":feature:movie"))
	implementation(project(":feature:recommendations"))
	implementation(project(":feature:about"))
	implementation(project(":arch"))
	implementation(project(":impl:database"))
}