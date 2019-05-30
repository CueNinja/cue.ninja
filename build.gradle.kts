plugins {
    application
    kotlin("jvm") version "1.3.31"
//    id("org.javamodularity.moduleplugin") version "1.5.0"
    id("org.openjfx.javafxplugin") version "0.0.7"
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

javafx {
    modules(
        "javafx.graphics",
        "javafx.controls",
        "javafx.fxml",
        "javafx.web"
    )
}

dependencies {
    compile(kotlin("stdlib"))
    compile("org.openjfx:javafx:11.0.2")
    compile("org.controlsfx:controlsfx:11.0.0")
    compile("de.jensd:fontawesomefx-commons:11.0")
    compile("de.jensd:fontawesomefx-fontawesome:4.7.0-11")
    compile("org.glassfish:jakarta.json:1.1.5")
    compile("io.reactivex.rxjava2:rxjava:2.2.8")
    compile("com.github.thomasnield:rxkotlinfx:2.2.2")
    compile("ninja.cue:monaco.editor:0.3-SNAPSHOT")

    compile("org.postgresql:postgresql:42.2.5")
    compile("de.codecentric.centerdevice:centerdevice-nsmenufx:2.1.6")
    compile("org.fxmisc.richtext:richtextfx:0.10.1")
    //--------------------- Devtools ---------------------
//    compile("ninja.cue:javafx-webview-debugger:0.6.0-SNAPSHOT")
}
