package org.example.appfichaje.controller;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.appfichaje.HelloApplication;
import org.example.appfichaje.data.DataSet;
import org.example.appfichaje.model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PerfilController extends BaseController implements Initializable {

    @FXML
    private Button btnCargarNomina;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEmail;

    @FXML
    private Button btnRegistrarJornada;

    @FXML
    private Text diasVacaciones;

    @FXML
    private BorderPane escenaPerfil;

    @FXML
    private Text horasTotales;

    @FXML
    private FontAwesomeIconView iconoRegistro;

    @FXML
    private ImageView imgEmpleado;

    @FXML
    private VBox panelFondo;

    @FXML
    private VBox panelHoras;

    @FXML
    private VBox panelVacaciones;

    @FXML
    private Text txtFechaAlta;

    @FXML
    private Text txtJornada;

    @FXML
    private Text txtNombre;

    @FXML
    private Text txtApellido;

    @FXML
    private Text txtProyecto;

    @FXML
    private Text txtPuesto;

    @FXML
    private Text txtTLF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        actions();
    }

    private void actions() {
        btnEditar.setOnMouseClicked(event -> {
            Usuario usuarioSeleccionado = DataSet.getUsuarioActual();
            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("cambiar-datos-view.fxml"));
                Parent root = loader.load();

                CambiarDatosController controller = loader.getController();
                controller.cargarUsuario(usuarioSeleccionado);

                HelloApplication.scene.setRoot(root);

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("La pantalla que se intenta cargar no está disponible");
                alert.show();
            }
        });
    }

    private void instances() {
        Usuario usuario = DataSet.getUsuarioActual();

        if (usuario != null) {
            setUsuario(usuario);
        }
    }

    public void setUsuario(Usuario usuario) {
        txtNombre.setText(usuario.getNombre());
        txtApellido.setText(usuario.getApellidos());
        txtPuesto.setText(usuario.getPuestoTrabajo());
        txtProyecto.setText(usuario.getProyectoAsignado());
        txtTLF.setText(usuario.getTelefono());
        txtJornada.setText(String.valueOf(usuario.getJornada()));
        txtFechaAlta.setText(String.valueOf(usuario.getFechaAlta()));
        btnEmail.setText(usuario.getCorreo());
    }
}
