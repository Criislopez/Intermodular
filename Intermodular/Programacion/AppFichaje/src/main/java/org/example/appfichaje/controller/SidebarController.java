package org.example.appfichaje.controller;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.appfichaje.HelloApplication;

public class SidebarController {

    @FXML
    private Button brnInicio;

    @FXML
    private Button btnPerfil;

    @FXML
    private Button btnEquipo;

    @FXML
    private Button btnSalir;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        brnInicio.setOnAction(e -> navigate("inicio-view.fxml"));
        btnPerfil.setOnAction(e -> navigate("perfil-view.fxml"));
        btnEquipo.setOnAction(e -> navigate("lista-usuarios-view.fxml"));
        btnSalir.setOnAction(e ->  navigate("login-view.fxml"));
    }

    private void navigate(String fxmlPath) {
        try {
            HelloApplication.setRoot(fxmlPath);
        } catch (Exception e) {
            System.err.println("Error al cargar: " + fxmlPath);
            e.printStackTrace();
        }
    }
}