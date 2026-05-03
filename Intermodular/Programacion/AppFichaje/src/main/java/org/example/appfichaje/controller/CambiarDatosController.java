package org.example.appfichaje.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.example.appfichaje.dao.UsuarioDAO;
import org.example.appfichaje.data.DataSet;
import org.example.appfichaje.model.Rol;
import org.example.appfichaje.model.TipoJornada;
import org.example.appfichaje.model.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

public class CambiarDatosController extends BaseController implements Initializable {

    @FXML
    private Button btnModificarUsuario;


    @FXML
    private BorderPane panelGeneral;


    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDNI;

    @FXML
    private DatePicker pickerFechaAlta;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPuestoTrabajo;

    @FXML
    private TextField txtTelefono;

    @FXML
    private TextField txtPass;

    @FXML
    private ComboBox<Rol> comboRol;
    private ObservableList<Rol> listaRol;

    @FXML
    private ComboBox<TipoJornada> comboJornada;
    private ObservableList<TipoJornada> listaJornada;

    @FXML
    private TextField txtProyecto;

    private Usuario usuarioActual;

    private UsuarioDAO usuarioDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        initGUI();
        actions();
    }


    private void actions() {
        btnModificarUsuario.setOnAction(event -> {
            if (usuarioActual == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Sin usuario", "No hay ningún usuario seleccionado.");
                return;
            }

            aplicarCambios();
            //Cambiar los datos en bbdd
            usuarioDAO.updateUser(usuarioActual);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Cambios guardados", "Los datos del usuario se han actualizado correctamente.");
        });
    }

    private void aplicarCambios() {
        if (!txtNombre.getText().isEmpty())         usuarioActual.setNombre(txtNombre.getText());
        if (!txtApellido.getText().isEmpty())       usuarioActual.setApellidos(txtApellido.getText());
        if (!txtCorreo.getText().isEmpty())         usuarioActual.setCorreo(txtCorreo.getText());
        if (!txtDNI.getText().isEmpty())            usuarioActual.setDNI(txtDNI.getText());
        if (!txtPass.getText().isEmpty())           usuarioActual.setPass(txtPass.getText());
        if (!txtTelefono.getText().isEmpty())       usuarioActual.setTelefono(txtTelefono.getText());
        if (!txtPuestoTrabajo.getText().isEmpty())  usuarioActual.setPuestoTrabajo(txtPuestoTrabajo.getText());
        if (!txtProyecto.getText().isEmpty())       usuarioActual.setProyectoAsignado(txtProyecto.getText());
        if (pickerFechaAlta.getValue() != null)     usuarioActual.setFechaAlta(pickerFechaAlta.getValue());

        Rol rolSeleccionado = comboRol.getSelectionModel().getSelectedItem();
        if (rolSeleccionado != null) usuarioActual.setRol(rolSeleccionado);

        TipoJornada jornadaSeleccionada = comboJornada.getSelectionModel().getSelectedItem();
        if (jornadaSeleccionada != null) usuarioActual.setJornada(jornadaSeleccionada);

        DataSet.setUsuarioActual(usuarioActual);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.show();
    }

    private void initGUI() {
        comboRol.setItems(listaRol);
        comboJornada.setItems(listaJornada);

    }

    private void instances() {
        listaRol = FXCollections.observableArrayList(Rol.ADMIN, Rol.EMPLEADO);
        listaJornada = FXCollections.observableArrayList(TipoJornada.COMPLETA, TipoJornada.PARCIAL);
        usuarioDAO = new UsuarioDAO();
    }

    public void cargarUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        txtNombre.setPromptText(usuario.getNombre());
        txtApellido.setPromptText(usuario.getApellidos());
        txtCorreo.setPromptText(usuario.getCorreo());
        txtDNI.setPromptText(usuario.getDNI());
        txtPass.setPromptText("••••••••");
        txtTelefono.setPromptText(usuario.getTelefono());
        txtPuestoTrabajo.setPromptText(usuario.getPuestoTrabajo());
        txtProyecto.setPromptText(usuario.getProyectoAsignado());
        comboRol.getSelectionModel().select(usuario.getRol());
        comboJornada.getSelectionModel().select(usuario.getJornada());
        pickerFechaAlta.setValue(usuario.getFechaAlta());
    }
}

