package org.example.appfichaje.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.appfichaje.HelloApplication;
import org.example.appfichaje.dao.UsuarioDAO;
import org.example.appfichaje.data.DataSet;
import org.example.appfichaje.model.Usuario;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button btnAcceder;

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtPass;

    @FXML
    private Text txtErrorCredenciales;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        actions();
    }

    private void instances() {
        UsuarioDAO dao = new UsuarioDAO();
        DataSet.setListaUsuarios(
                FXCollections.observableArrayList(dao.getAllUsers())
        );
    }


    private  void actions(){

        btnAcceder.setOnAction(actionEvent -> {

            if (txtUser.getText().isEmpty() || txtPass.getText().isEmpty()) {
                txtErrorCredenciales.setText("Usuario o contraseña incorrectos");
                return;
            }

            Usuario usuarioLogin = DataSet.getLogin(txtUser.getText(), txtPass.getText());

            if (usuarioLogin != null) {
                DataSet.setUsuarioActual(usuarioLogin);

                try {
                    FXMLLoader loader = new FXMLLoader(
                            HelloApplication.class.getResource("inicio-view.fxml")
                    );

                    Parent root = loader.load();

                    InicioController pantallaPerfil = loader.getController();
                    pantallaPerfil.setUsuario(usuarioLogin);

                    HelloApplication.scene.setRoot(root);

                } catch (IOException e) {
                    System.out.println(e);
                }
            } else {
                txtErrorCredenciales.setText("Usuario o contraseña incorrectos");
            }
        });
    }
}
