@file:Suppress("UNUSED")
package io.astrum.mindsync.buildlogic.common.extensions

import io.astrum.mindsync.buildlogic.common.AppConfiguration
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val Project.libs: VersionCatalog get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

val Project.fullPackageName get() = AppConfiguration.packageName + path.replace(':', '.')

fun Project.catalogVersion(alias: String) = libs.findVersion(alias).get().toString()
fun Project.catalogLib(alias: String) = libs.findLibrary(alias).get()
fun Project.catalogBundle(alias: String) = libs.findBundle(alias).get()

fun KotlinDependencyHandler.catalogVersion(alias: String) = project.catalogVersion(alias)
fun KotlinDependencyHandler.catalogLib(alias: String) = project.catalogLib(alias)
fun KotlinDependencyHandler.catalogBundle(alias: String) = project.catalogBundle(alias)

fun KotlinDependencyHandler.implementation(
    dependencyNotation: Provider<*>,
    configure: ExternalModuleDependency.() -> Unit
) {
    implementation(dependencyNotation.get().toString(), configure)
}

fun DependencyHandlerScope.implementation(
    provider: Provider<*>,
    dependencyConfiguration: ExternalModuleDependency.() -> Unit = {},
) {
    "implementation"(provider, dependencyConfiguration)
}


fun Project.coerceComposeVersion(configuration: Configuration) {
    val independentGroups = setOf("compiler", "material3")
    configuration.resolutionStrategy.eachDependency {
        if (requested.group.startsWith("androidx.compose") && independentGroups.none(requested.group::contains)) {
            useVersion(libs.findVersion("compose").get().requiredVersion)
            because("I need the changes in lazyGrid")
        }
    }
}

fun TaskContainer.commonTasks() {
    withType<JavaCompile>().configureEach {
        sourceCompatibility = AppConfiguration.jvmTargetStr
        targetCompatibility = AppConfiguration.jvmTargetStr
    }
    withType<KotlinCompile>().configureEach {
        project.configureKotlinJvm()
    }
}
