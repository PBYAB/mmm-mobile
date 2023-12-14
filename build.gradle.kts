// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.openapi.generator") version "5.3.0"
}

openApiGenerate {
    generatorName.set("kotlin")
    inputSpec.set("$rootDir/openapi.json")
    outputDir.set("$rootDir/app")
    apiPackage.set("com.example.mmm_mobile.data.api")
    modelPackage.set("com.example.mmm_mobile.data.entities")
    configOptions.put("dateLibrary", "java8")
    configOptions.put("useOptional", "true")
    configOptions.put("serializationLibrary", "kotlinx_serialization")
    configOptions.put("library", "kotlin")
    configOptions.put("library", "jvm-retrofit2")
}