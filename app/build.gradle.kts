@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
	id(libs.plugins.com.android.application.get().pluginId)
	id(libs.plugins.org.jetbrains.kotlin.android.get().pluginId)
}

android {
	namespace = "com.pmorozova.movies"
	compileSdk = Config.compileSdk

	defaultConfig {
		applicationId = "com.pmorozova.movies"
		minSdk = Config.minSdk
		targetSdk = Config.targetSdk
		versionCode = Config.versionCode
		versionName = Config.versionName

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		vectorDrawables {
			useSupportLibrary = true
		}
	}

	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}
	compileOptions {
		sourceCompatibility = Config.javaVersion
		targetCompatibility = Config.javaVersion
	}
	kotlinOptions {
		jvmTarget = Config.jvmTarget.target
	}
	buildFeatures {
		compose = true
	}
	composeOptions {
		kotlinCompilerExtensionVersion = Config.kotlinCompilerExtensionVersion
	}
	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}

dependencies {
	implementation(project(":impl:navigation"))
	implementation(libs.core.ktx)
	implementation(libs.lifecycle.runtime.ktx)
	implementation(libs.activity.compose)
	implementation(libs.material3)
	implementation(libs.koin.core)
	implementation(libs.koin.android)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.test.ext.junit)
	androidTestImplementation(libs.espresso.core)
	androidTestImplementation(platform(libs.compose.bom))
	androidTestImplementation(libs.ui.test.junit4)
	debugImplementation(libs.ui.tooling)
	debugImplementation(libs.ui.test.manifest)
	implementation(project(":impl:database"))
	implementation(project(":impl:network"))
	implementation(project(":feature:movie"))
	implementation(project(":feature:favorites"))
	implementation(project(":feature:recommendations"))
	implementation(project(":data:movie"))
	implementation(project(":data:favorites"))
	implementation(project(":data:search"))
	implementation(project(":common"))
	implementation(project(":domain:models"))
}