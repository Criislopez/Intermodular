package org.example.appfichaje.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.example.appfichaje.controller.SidebarController;

public abstract class BaseController {

    @FXML
    protected SidebarController sidebarController; // inyectado automáticamente por fx:include

    protected Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
        if (sidebarController != null) {
            sidebarController.setStage(stage);
        }
    }
}