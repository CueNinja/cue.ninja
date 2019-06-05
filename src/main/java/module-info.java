module ninja.cue {
    requires java.sql;
    requires java.sql.rowset;
    requires java.xml.crypto;
    requires javafx.base;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;

    requires de.jensd.fx.fontawesomefx.commons;
    requires de.jensd.fx.fontawesomefx.materialicons;
    requires org.controlsfx.controls;
    requires org.reactivestreams;
    requires io.reactivex.rxjava2;
    requires kotlin.stdlib;
    requires rxkotlinfx;
    requires rxjavafx;
    requires flowless;
    requires wellbehavedfx;
    requires richtextfx;
    requires com.fasterxml.jackson.module.kotlin;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires json.schema.validator;
    requires org.antlr.antlr4.runtime;

    exports ninja.cue;
    opens ninja.cue.views;
}