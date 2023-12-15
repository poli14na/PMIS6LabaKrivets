import Config.jvmTarget
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Project

fun Project.configureAndroid(
	commonExtension: CommonExtension<*, *, *, *, *>, // количество звездочек постоянно меняется тут. Это рейтинг градл-плагина просто, вы не понимаете.
) = commonExtension.apply {
	compileSdk = Config.compileSdk

	defaultConfig {
		minSdk = Config.minSdk

		testInstrumentationRunner = Config.testRunner
		vectorDrawables {
			useSupportLibrary = true
		}
		proguardFiles(Config.proguardFile)
	}

	buildTypes {
		this.getByName("release") {
			isMinifyEnabled = Config.isMinifyEnabledRelease
			proguardFiles(
				getDefaultProguardFile(Config.defaultProguardFile),
				Config.proguardFile
			)
		}
	}
	compileOptions {
//		sourceCompatibility = Config.javaVersion
//		targetCompatibility = Config.javaVersion
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

fun Project.configureAndroidLibrary(variant: LibraryExtension) = variant.apply {
	configureAndroid(this)

	defaultConfig {
		consumerProguardFiles(file(Config.consumerProguardFile))
	}

	buildFeatures {
		buildConfig = false
		compose = false
		androidResources = true // required for R8 to work
	}

	libraryVariants.all {
		sourceSets {
			getByName(name) {
				kotlin.srcDir("build/generated/ksp/$name/kotlin")
			}
		}
	}
}

fun Project.configureAndroidApplication(variant: BaseAppModuleExtension) = variant.apply {
	configureAndroid(this)
	applicationVariants.all {
		sourceSets {
			getByName(name) {
				kotlin.srcDir("build/generated/ksp/$name/kotlin")
			}
		}
	}
}