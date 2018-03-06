plugins {
    application
    kotlin("jvm") version "1.2.30"
}
application {
    mainClassName = "ninja.cue.Main"
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
    compile("no.tornado:tornadofx:1.7.15")
    compile("org.controlsfx:controlsfx:9.0.0")
    compile("com.github.thomasnield:rxkotlinfx:2.2.2")
    compile("javax.json:javax.json-api:1.1.2")
    compile("de.jensd:fontawesomefx-commons:9.1.2")
    compile("de.jensd:shichimifx:1.2.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_9
    targetCompatibility = JavaVersion.VERSION_1_9
}
