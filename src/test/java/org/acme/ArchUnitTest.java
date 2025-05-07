package org.acme;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.ws.rs.Path;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "org.acme", importOptions = {ImportOption.DoNotIncludeTests.class})
public class ArchUnitTest {

    @ArchTest
    static ArchRule allResourceShouldHavePaths = classes()
            .that()
            .resideInAPackage("..resource..")
            .should().beAnnotatedWith(Path.class);

    @ArchTest
    static ArchRule test = classes().that().haveNameMatching(".*Resource").should().resideInAPackage("..resource");

    @ArchTest
    static ArchRule layerd = layeredArchitecture()
            .consideringAllDependencies()
            .layer("Resource").definedBy("..resource..")
            .layer("Service").definedBy("..service..")
            .layer("Persistence").definedBy("..entity..")

            .whereLayer("Resource").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Resource")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service");
}
