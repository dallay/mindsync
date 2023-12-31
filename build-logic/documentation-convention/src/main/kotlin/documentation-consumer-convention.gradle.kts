import io.gitlab.arturbosch.detekt.Detekt
import org.asciidoctor.gradle.jvm.AsciidoctorTask
import org.gradle.kotlin.dsl.named
import org.jetbrains.dokka.gradle.DokkaMultiModuleTask
import org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension
import org.owasp.dependencycheck.gradle.tasks.Aggregate

plugins{
    id("org.jetbrains.dokka")
}
val asciidoc: Configuration by configurations.creating {
    isCanBeResolved = true
    isVisible = true
    isCanBeConsumed = false
    attributes {
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("asciidoc-html-folder"))
    }
}

val dokkaHtmlMultiModuleTask = tasks.named<DokkaMultiModuleTask>(
    "dokkaHtmlMultiModule"
)

//val testReportTask = tasks.named<TestReport>("testReport")
//val jacocoReportTask = tasks.named<JacocoReport>("testCodeCoverageReport")
val detektReportTask = tasks.named<Detekt>("detekt")
val dependencyCheckTask = tasks.named<Aggregate>("dependencyCheckAggregate")

tasks.register("aggregateReports") {
    dependsOn(dokkaHtmlMultiModuleTask)
//    dependsOn(testReportTask)
//    dependsOn(jacocoReportTask)
    dependsOn(detektReportTask)

    doLast {
        val targetDir = layout.buildDirectory.dir("documentation").get().asFile.toPath()
        copy {
            into(targetDir.resolve("dokka"))
            from(dokkaHtmlMultiModuleTask.map { task -> task.outputDirectory })
        }

//        copy {
//            into(targetDir.resolve("tests"))
//            from(testReportTask.map { task -> task.outputs })
//        }
//
//        copy {
//            into(targetDir.resolve("jacoco"))
//            from(jacocoReportTask.map { task -> task.outputs })
//        }

        copy {
            into(targetDir.resolve("detekt"))
            from(detektReportTask.map { task -> task.outputs })
        }
    }
}

tasks.register("aggregateDocumentation") {
    asciidoc.dependencies
        .filterIsInstance<ProjectDependency>()
        .map { it.dependencyProject.tasks.withType<AsciidoctorTask>() }
        .forEach { dependsOn(it) }

    doLast {
        val targetDir = layout.buildDirectory.dir("documentation").get().asFile.toPath()

        copy {
            into(targetDir.resolve("owasp"))
            project.extensions.findByType<DependencyCheckExtension>()?.let {
                from(it.outputDirectory)
            }
        }

        copy {
            into(targetDir.resolve("pages"))
            from(asciidoc.incoming.artifactView { lenient(true) }.files)
        }

        copy {
            into(targetDir)
            from("documentation/src/documentation")
        }
    }
}
