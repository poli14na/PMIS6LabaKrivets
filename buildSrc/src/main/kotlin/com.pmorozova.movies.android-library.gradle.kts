plugins {
	id("com.android.library")
	kotlin("android")
}
val libs by versionCatalog

android {
	namespace = Config.applicationId
	configureAndroidLibrary(this)
}

dependencies {
	implementation(libs.requireLib("core-ktx"))
	implementation(libs.requireLib("koin-core"))
}