package com.amigoscode.devicemanagement.archtest;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;


@AnalyzeClasses(packages = "pl.sages.javadevpro.projecttwo",
        importOptions = {ImportOption.DoNotIncludeTests.class})
class DomainArchitectureTest {


    @ArchTest
    static final ArchRule domain_has_no_external_dependencies = noClasses().that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideOutsideOfPackages("..domain..", "..java..", "", "..lombok..");
    // the package "" is where byte[] resides (=> https://stackoverflow.com/questions/67298013/archunit-base-type-thrown-wrong)

    @ArchTest
    static final ArchRule task_is_not_dependent_on_user = noClasses().that().resideInAPackage("..domain.task..")
            .should().dependOnClassesThat().resideInAPackage("..domain.user..");


}
