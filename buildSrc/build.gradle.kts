plugins {
	`kotlin-dsl` // особые плагины для "прекомпиляции" и написания gradle DSL
	`kotlin-dsl-precompiled-script-plugins`
}

dependencies {
	implementation(libs.android.gradle)
	implementation(libs.kotlin.gradle)
}

