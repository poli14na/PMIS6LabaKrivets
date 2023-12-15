import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object Config {

	const val applicationId = "com.pmorozova.movies"
	const val versionCode = 1

	const val majorRelease = 1
	const val minorRelease = 0
	const val patch = 0
	const val versionName = "$majorRelease.$minorRelease.$patch ($versionCode)"
	val javaVersion = JavaVersion.VERSION_1_8
	val jvmTarget = JvmTarget.JVM_1_8
	const val compileSdk = 34
	const val targetSdk = compileSdk
	const val minSdk = 26
	val kotlinLanguageVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_9

	const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
	const val isMinifyEnabledRelease = true
	const val isMinifyEnabledDebug = false
	const val defaultProguardFile = "proguard-android-optimize.txt"
	const val proguardFile = "proguard-rules.pro"
	const val consumerProguardFile = "consumer-rules.pro"

	const val kotlinCompilerExtensionVersion = "1.5.5"

	val compilerArgs = listOf(
		"-Xbackend-threads=0", // parallel IR compilation
	)
	val optIns = listOf(
		"kotlinx.coroutines.ExperimentalCoroutinesApi",
		"kotlinx.coroutines.FlowPreview",
		"kotlin.RequiresOptIn",
		"kotlin.ExperimentalStdlibApi",
		"kotlin.experimental.ExperimentalTypeInference",
		"kotlin.contracts.ExperimentalContracts",
		"androidx.compose.animation.ExperimentalAnimationApi",
		"androidx.compose.material3.ExperimentalMaterial3Api"
	)
	val jvmCompilerArgs = buildList {
		addAll(compilerArgs)
		add("-Xjvm-default=all") // enable all jvm optimizations
		add("-Xcontext-receivers")
		add("-Xstring-concat=inline") // fix kotlin-serialization in library modules issue
		addAll(optIns.map { "-opt-in=$it" })
	}
}