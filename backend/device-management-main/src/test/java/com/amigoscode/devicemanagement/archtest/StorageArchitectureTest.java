package com.amigoscode.devicemanagement.archtest;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;


@AnalyzeClasses(packages = "com.amigoscode.devicemanagement",
        importOptions = {ImportOption.DoNotIncludeTests.class})
class StorageArchitectureTest {

    @ArchTest
    static final ArchRule entity_class_names_are_only_used_in_storage_layer = classes().that().haveNameMatching(".*Entity")
            .should().resideInAPackage("..storage..");

    @ArchTest
    static final ArchRule entity_classes_are_not_used_outside_storage_layer = classes().that().areAnnotatedWith(DynamoDBTable.class)
            .should().onlyBeAccessed().byAnyPackage("..storage..");

    @ArchTest
    static final ArchRule jpa_annotations_are_only_used_in_storage_layer = noClasses().that().resideOutsideOfPackage("..storage..")
            .should().dependOnClassesThat().resideInAnyPackage("..javax.persistence..", "..hibernate..");


}
