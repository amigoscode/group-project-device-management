package com.amigoscode.devicemanagement.archtest;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.amigoscode.devicemanagement",
        importOptions = {ImportOption.DoNotIncludeTests.class})
class GeneralCodingRulesTest {


    @ArchTest
    static final ArchRule no_classes_should_throw_generic_exceptions =
            GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

    @ArchTest
    static final ArchRule beans_should_not_be_injected_by_field =
            GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

    @ArchTest
    static final ArchRule deprecated_classes_should_not_be_used = noClasses().that()
            .areAnnotatedWith(Deprecated.class).should()
            .resideInAnyPackage("com.amigoscode.devicemanagement");


}
