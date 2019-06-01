module ninja.cue {
    requires java.json;
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

    exports ninja.cue;
    opens ninja.cue.views;
}