plugins {
	id("com.pmorozova.movies.android-library")
	id("org.jetbrains.kotlin.plugin.serialization")
}

val libs by versionCatalog

dependencies {
	implementation(libs.requireLib("ktor"))
	implementation(libs.requireLib("ktor-auth"))
	implementation(libs.requireLib("ktor-client-core"))
	implementation(libs.requireLib("ktor-client-cio"))
	implementation(libs.requireLib("ktor-client-logging"))
	implementation(libs.requireLib("ktor-client-json"))
	implementation(libs.requireLib("ktor-serialization-kotlinx-json"))
	implementation(libs.requireLib("ktor-client-content-negotiation"))
//	implementation(libs.requireLib("ktor-serialization-kotlinx"))
}