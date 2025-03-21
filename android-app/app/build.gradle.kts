import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.kotlinxSerialization)
	alias(libs.plugins.google.devtools.ksp)
	alias(libs.plugins.detekt)
	alias(libs.plugins.ktlint.wrapper.plugin)
	alias(libs.plugins.google.dagger.hilt)
	id("androidx.room")
}

buildscript {
	dependencies {
		classpath(libs.hilt.android.gradle.plugin)
	}
}

android {
	namespace = "rs.raf.rafeisen"
	compileSdk = 35

	defaultConfig {
		applicationId = "rs.raf.rafeisen"
		minSdk = 26
		targetSdk = 35
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

		javaCompileOptions {
			annotationProcessorOptions {
			}
		}
	}

	room {
		schemaDirectory("$projectDir/schemas")
	}

	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
	buildFeatures {
		compose = true
	}
}

subprojects {
	apply(plugin = "org.jlleitschuh.gradle.ktlint")
	configure<KtlintExtension> {
		version = "1.0.1"
		android = true
		verbose = true
	}

	apply(plugin = "io.gitlab.arturbosch.detekt")
	detekt {
		buildUponDefaultConfig = true
		allRules = false
		config.setFrom("$rootDir/detekt.yml")
	}
}

dependencies {
	detektPlugins(libs.ktlint.compose.rules)
	ktlintRuleset(libs.ktlint.compose.rules)

	ksp(libs.hilt.compiler.android)
	ksp(libs.hilt.compiler.dagger)

	ksp(libs.androidx.room.compiler)
	implementation(libs.androidx.room.runtime)
	implementation(libs.androidx.room.ktx)
	implementation(libs.androidx.room.runtime)
	implementation(libs.sqlcipher.android)


	ksp(libs.hilt.compiler)
	implementation(libs.navigation.hilt)
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.datastore)
	implementation(libs.androidx.datastore.preferences)
	implementation(libs.androidx.material.icons.extended)
	implementation(libs.hilt.android)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	implementation(libs.kotlinx.serialization.json)
	implementation(libs.androidx.navigation.compose)
	implementation(libs.okhttp)
	implementation(libs.okhttp.logging.interceptor)
	implementation(libs.retrofit)
	implementation(libs.retrofit.converter.scalars)
	implementation(libs.retrofit.serialization.converter)
	implementation(libs.core.splashscreen)
	implementation(libs.timber)
	implementation(libs.kotlin.onetimepassword)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
	testImplementation(kotlin("test"))
}
