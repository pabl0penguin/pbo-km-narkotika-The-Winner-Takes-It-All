module com.example.tugasbesarpbonarikotikac {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.swing;

    exports com.example.tugasbesarpbonarikotikac.app;
    opens com.example.tugasbesarpbonarikotikac.app to javafx.fxml;
    exports com.example.tugasbesarpbonarikotikac.view;
    opens com.example.tugasbesarpbonarikotikac.view to javafx.fxml;
}