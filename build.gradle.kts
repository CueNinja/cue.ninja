import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//val javaHome = System.getProperty("java.home")
//val kotlinVersion = "1.3.31"
val moduleName by extra("ninja.cue")

plugins {
    application
//    java //for jlink
    kotlin("jvm") version "1.3.31"
    id("org.openjfx.javafxplugin") version "0.0.7"
}
application {
    mainClassName = "$moduleName/ninja.cue.Main"
}

repositories {
    mavenCentral()
    jcenter()
    maven {
        setUrl("https://maven.cue.ninja")
    }
}

javafx {
    modules(
        "javafx.base",
        "javafx.graphics",
        "javafx.controls",
        "javafx.fxml"
    )
}

dependencies {
    compile(kotlin("stdlib"))
//    api("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion:modular") //for jlink
    compile("org.openjfx:javafx:11.0.2")
    compile("org.controlsfx:controlsfx:11.0.0")
    compile("de.jensd:fontawesomefx-commons:11.0")
    compile("de.jensd:fontawesomefx-materialicons:2.2.0-11")
    compile("org.glassfish:jakarta.json:1.1.5")
    compile("io.reactivex.rxjava2:rxjava:2.2.8")
    compile("com.github.thomasnield:rxkotlinfx:2.2.2")

    compile("org.postgresql:postgresql:42.2.5")
    compile("de.codecentric.centerdevice:centerdevice-nsmenufx:2.1.6")
    compile("org.fxmisc.richtext:richtextfx:0.10.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType(KotlinCompile::class) {
    kotlinOptions.jvmTarget = "11"
}
/*
//All this is for jlink, all deps need to have module-info though!
tasks {
   "compileKotlin"(KotlinCompile::class) {

   }
   "compileJava"(JavaCompile::class) {
       inputs.property("moduleName", moduleName)
       doFirst {
          options.compilerArgs = listOf(
                   "--module-path", classpath.asPath
                   "--patch-module", "$moduleName=${sourceSets["main"].output.asPath}"
           )
           classpath = files()
       }
   }
   val link by registering(type=Exec::class) {
       val outputDir by extra("$buildDir/jlink")
       inputs.files(configurations.runtimeClasspath)
       inputs.files(jar)
       outputs.dir(outputDir)
       dependsOn(jar)
       doFirst {
           val runtimeClasspath = configurations.runtimeClasspath.get()
           delete(outputDir)
           commandLine(
                   "$javaHome/bin/jlink",
                   "--module-path",
                   listOf("$javaHome/jmods/", runtimeClasspath.asPath, "$buildDir/libs/${project.name}.jar").joinToString(File.pathSeparator),
                   "--add-modules", moduleName,
                   "--output", outputDir
           )
       }
   }
   withType(KotlinCompile::class) {
       doFirst {
           kotlinOptions.freeCompilerArgs = listOf(
                   "-Xmodule-path=${classpath.asPath}",
                   "-Xjsr305=strict"
           )
           classpath = files()
       }
       kotlinOptions {
           freeCompilerArgs = listOf("-Xjsr305=strict")
           jvmTarget = "11"
       }
   }
   withType(JavaExec::class) {
       dependsOn(dependsOn.intersect(setOf(link)))
   }
}
*/