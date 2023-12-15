pluginManagement {
	repositories {
		google()
		mavenCentral()
		gradlePluginPortal()
	}
}
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
	}
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "Morozova"

include(
	":app",
	":impl:network",
	":impl:navigation",
//	":impl:local-storage",
	"arch",
	":impl:database",
	":domain:navigator",
	":feature:about",
	":feature:movie",
	":feature:favorites",
	":feature:recommendations",
	":feature:search",
	":data:favorites",
	":data:movie",
	":data:search",
	":common",
	":domain:models"
)