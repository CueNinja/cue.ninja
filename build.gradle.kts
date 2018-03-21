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
    maven {
        setUrl("https://maven.cue.ninja")
    }
}

dependencies {
    compile(kotlin("stdlib"))
    compile("no.tornado:tornadofx:1.7.15")
    compile("org.controlsfx:controlsfx:9.0.0")
    compile("com.github.thomasnield:rxkotlinfx:2.2.2")
    compile("javax.json:javax.json-api:1.1.2")
    compile("de.jensd:fontawesomefx-commons:9.1.2")
    compile("de.jensd:shichimifx:1.2.2")
    compile("ninja.cue:monaco.editor:0.1-SNAPSHOT")

    compile("org.postgresql:postgresql:42.2.1")
    //--------------------- Devtools ---------------------
    compile("ninja.cue:javafx-webview-debugger:0.6.0-SNAPSHOT")
}
