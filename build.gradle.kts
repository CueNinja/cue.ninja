plugins {
    application
    kotlin("jvm") version "1.2.31"
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
    compile("org.controlsfx:controlsfx:9.0.0")
    compile("de.jensd:fontawesomefx-commons:9.1.2")
    compile("de.jensd:fontawesomefx-fontawesome:4.7.0-9.1.2")
    compile("javax.json:javax.json-api:1.1.2")
//    compile("de.jensd:shichimifx:1.2.2")
    compile("ninja.cue:monaco.editor:0.2-SNAPSHOT")

    compile("org.postgresql:postgresql:42.2.1")
    compile("de.codecentric.centerdevice:centerdevice-nsmenufx:2.1.6")
    //--------------------- Devtools ---------------------
//    compile("ninja.cue:javafx-webview-debugger:0.6.0-SNAPSHOT")
}
