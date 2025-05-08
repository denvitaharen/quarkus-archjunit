package org.acme;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

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

    @ArchTest
    static ArchRule publicMethodsMustHaveOpenApiRespons = ArchRuleDefinition.methods().that()
            .areDeclaredInClassesThat().resideInAPackage("..resource")
            .and().arePublic()
            .should()
                .beAnnotatedWith(APIResponse.class)
            .orShould()
                .beAnnotatedWith(APIResponses.class);
}
