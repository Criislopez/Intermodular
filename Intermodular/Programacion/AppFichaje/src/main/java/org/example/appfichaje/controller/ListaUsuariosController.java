package org.example.appfichaje.controller;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import org.example.appfichaje.HelloApplication;
import org.example.appfichaje.data.DataSet;

import org.example.appfichaje.model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListaUsuariosController extends BaseController implements Initializable {

    @FXML
    private Button btnAgregarUsuario;


    @FXML
    private ListView<Usuario> listUsuarios;
    private ObservableList<Usuario> listaUsuarios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        initGUI();
        actions();
    }

    private void actions() {
        listUsuarios.setOnMouseClicked(event -> {
            if(event.getClickCount() ==2) {
                Usuario usuarioSeleccionado = listUsuarios.getSelectionModel().getSelectedItem();

                try {

                    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("perfil-view.fxml"));
                    Parent root = loader.load();

                    PerfilController perfilController = loader.getController();
                    perfilController.setUsuario(usuarioSeleccionado);

                    HelloApplication.scene.setRoot(root);

                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("La pantalla que se intenta cargar no está disponible");
                    alert.show();
                }

            }
        });

        btnAgregarUsuario.setOnAction(actionEvent -> {
            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("crear-usuario-view.fxml"));
                Parent root = loader.load();

                HelloApplication.scene.setRoot(root);

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("La pantalla que se intenta cargar no está disponible");
                alert.show();
            }
        });
    }

    private void initGUI() {
        listUsuarios.setItems(listaUsuarios);
    }

    private void instances() {
        listaUsuarios = DataSet.getListaUsuarios();
    }


}
