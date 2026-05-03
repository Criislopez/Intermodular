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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Scanner;


public class CrearUsuarioController extends BaseController implements Initializable {


    @FXML
    private Button btnAgregarUsuario;


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

    private UsuarioDAO usuarioDAO;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        initGUI();
        actions();

    }

    private void actions() {

        btnAgregarUsuario.setOnAction(event -> {

            Alert dialogo = null;
            if (txtNombre.getText().isEmpty()
                    || txtPass.getText().isEmpty()
                    || txtApellido.getText().isEmpty()
                    || txtCorreo.getText().isEmpty()
                    || txtDNI.getText().isEmpty()
                    || txtPass.getText().isEmpty()
                    || txtTelefono.getText().isEmpty()
                    || txtPuestoTrabajo.getText().isEmpty()
                    || pickerFechaAlta.getValue() == null
                    || comboRol.getSelectionModel().getSelectedIndex() == -1
                    || comboJornada.getSelectionModel().getSelectedIndex() == -1
            ) {
                dialogo = new Alert(Alert.AlertType.WARNING);
                dialogo.setTitle("Datos Vacíos");
                dialogo.setContentText("Confirma que todos los datos estén rellenos");

            } else {
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String correo = txtCorreo.getText();
                String DNI = txtDNI.getText();
                String pass = txtPass.getText();
                String telefono = txtTelefono.getText();
                LocalDate fechaAlta = pickerFechaAlta.getValue();
                TipoJornada jornada = comboJornada.getSelectionModel().getSelectedItem();
                Rol rol = comboRol.getSelectionModel().getSelectedItem();


                Usuario usuario = new Usuario(nombre, apellido, correo, DNI, pass, telefono, fechaAlta,jornada, rol);

                try {

                    usuarioDAO.insertUser(usuario);

                } catch (SQLException e) {
                    e.printStackTrace();
                    dialogo = new Alert(Alert.AlertType.ERROR);
                    dialogo.setTitle("Error al insertar");
                    dialogo.setContentText("Error: " + e.getMessage());
                }
                DataSet.addUsuario(usuario);


                dialogo = new Alert(Alert.AlertType.INFORMATION);
                dialogo.setTitle("Nuevo usuario agregado!");
                dialogo.setContentText("Usuario agregado con éxito.");
                vaciarCampos();
            }
            dialogo.show();

        });


    }


    private void initGUI() {
        comboRol.setItems(listaRol);
        comboJornada.setItems(listaJornada);
    }

    private void instances() {
        listaRol = FXCollections.observableArrayList(Rol.ADMIN, Rol.EMPLEADO);
        listaJornada =FXCollections.observableArrayList(TipoJornada.COMPLETA, TipoJornada.PARCIAL);
        usuarioDAO = new UsuarioDAO();
    }


    private void vaciarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
        txtPass.clear();
        txtPuestoTrabajo.clear();
        txtDNI.clear();
        txtTelefono.clear();
        txtProyecto.clear();
        txtPuestoTrabajo.clear();
        pickerFechaAlta.setValue(null);
        comboJornada.getSelectionModel().select(0);
        comboRol.getSelectionModel().select(0);
    }

    public void darAltaUsuario(Usuario usuario) {
        try {

            usuarioDAO.insertUser(usuario);
            System.out.println("Usuario dado de alta con exito");

        } catch (SQLException e) {
            System.out.println("Email duplicado, por favor mete otro mail");
            Scanner scanner = new Scanner(System.in);
            String email = scanner.next();
            usuario.setCorreo(email);
            darAltaUsuario(usuario);
        }
    }
}
