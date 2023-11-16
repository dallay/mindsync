import org.gradle.kotlin.dsl.configure
import org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension
import org.owasp.dependencycheck.reporting.ReportGenerator

plugins{
    id("org.owasp.dependencycheck")
}

configure<DependencyCheckExtension> {
    failBuildOnCVSS = 9F
    formats = listOf(
        ReportGenerator.Format.HTML.toString(),
        ReportGenerator.Format.JUNIT.toString(),
        ReportGenerator.Format.XML.toString(),
        ReportGenerator.Format.SARIF.toString()
    )
    suppressionFile = "${rootProject.rootDir}/config/owasp/owasp-supression.xml"

    // remove plugin dependencies, for configs see
    // https://docs.gradle.org/current/userguide/java_plugin.html#sec:java_plugin_and_dependency_management
    val validConfigurations = listOf("compileClasspath", "runtimeClasspath", "default")
    scanConfigurations = configurations.names
        .filter { validConfigurations.contains(it) }
        .toList()
    outputDirectory = layout.buildDirectory.dir("reports/owasp").get().asFile.absolutePath
}
