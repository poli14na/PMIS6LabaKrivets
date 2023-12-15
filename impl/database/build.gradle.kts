plugins {
	id("com.pmorozova.movies.android-library")
	id("com.google.devtools.ksp")
}

val libs by versionCatalog

//android {
//	configureAndroid(this)
//}

dependencies {
	implementation(libs.requireLib("androidx-room-rxjava3"))
	implementation(libs.requireLib("androidx-room-runtime"))
	implementation(project(":feature:favorites"))
	implementation(project(":feature:movie"))
	implementation(project(":feature:search"))
	implementation(project(":feature:recommendations"))
	implementation(project(":domain:models"))
	ksp(libs.requireLib("androidx-room-compiler"))
	implementation(libs.requireLib("androidx-room-ktx"))
	implementation(project(":impl:network"))
	implementation(project(":data:favorites"))
	implementation(project(":data:movie"))
	implementation(project(":common"))
}

ksp {
	arg("room.schemaLocation", "$projectDir/schema")
	arg("room.incremental", "true")
	arg("room.expandProjection", "true")
}