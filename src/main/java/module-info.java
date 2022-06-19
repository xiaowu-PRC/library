module com.library.library {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.base;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.desktop;
    requires rxcontrols;
    requires com.jfoenix;
    requires MaterialFX;
    requires kotlin.stdlib;

    opens com.library.library to javafx.fxml, javafx.base;
    opens util to javafx.base;
    exports com.library.library;
}