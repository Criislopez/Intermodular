module org.example.appfichaje {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires static lombok;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;


    opens org.example.appfichaje to javafx.fxml;
    exports org.example.appfichaje;
    exports org.example.appfichaje.controller;
    opens org.example.appfichaje.controller to javafx.fxml;
}