package org.example.appfichaje.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.Data;
import org.example.appfichaje.data.DataSet;
import org.example.appfichaje.model.Usuario;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

@Data
public class InicioController extends BaseController implements Initializable {

    private static final Locale LOCALE_ES = new Locale("es", "ES");
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("EEEE | d 'de' MMMM | yyyy", LOCALE_ES);

    @FXML
    private Button btnRegistrarJornada;


    @FXML
    private Button btnSolicitarVacaciones;

    @FXML
    private Button btnVerHistorial;

    @FXML
    private Label coma;

    @FXML
    private Label coma1;

    @FXML
    private TextArea descripcionProyecto;

    @FXML
    private Text diasRestantesVacaciones;

    @FXML
    private Label estadoFichaje;

    @FXML
    private Label horaActual;

    @FXML
    private FontAwesomeIconView iconoRegistro;

    @FXML
    private ImageView imgEmpleado;

    @FXML
    private ImageView imgProyecto;

    @FXML
    private Label labelAnyo;

    @FXML
    private Label labelDiaSemana;

    @FXML
    private Label labelDiaYMes;

    @FXML
    private Label localizacionProyecto;

    @FXML
    private HBox mitadAbajo;

    @FXML
    private HBox mitadArriba;

    @FXML
    private Text nombreEmpleado;

    @FXML
    private Label nombreProyecto;

    @FXML
    private HBox panelCentral;

    @FXML
    private VBox panelDerecha;

    @FXML
    private BorderPane panelGeneral;


    @FXML
    private VBox panelIzquierda;

    @FXML
    private Text puestoTrabajo;

    @FXML
    private Label responsableProyecto;

    @FXML
    private Text txtAprobacionRegistro;

    @FXML
    private Text apellidoEmpleado;

    @FXML
    private Label txtFechaAlta;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instances();
        iniciarReloj();
        iniciarFecha();
        actions();
    }

    private void instances() {
        Usuario usuario = DataSet.getUsuarioActual();

        if (usuario != null) {
            setUsuario(usuario);
        }
    }


    private void iniciarReloj() {
        Timeline reloj = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            LocalTime ahora = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            horaActual.setText(ahora.format(formatter));
        }));
        reloj.setCycleCount(Timeline.INDEFINITE);
        reloj.play();
    }

    private void iniciarFecha() {
        actualizarFecha();
        Timeline fecha = new Timeline(new KeyFrame(Duration.seconds(60), e -> actualizarFecha()));
        fecha.setCycleCount(Timeline.INDEFINITE);
        fecha.play();
    }

    private void actualizarFecha() {
        String[] partes = LocalDate.now().format(FORMATO_FECHA).split(" \\| ");

        labelDiaSemana.setText(capitalizar(partes[0]));
        labelDiaYMes.setText(capitalizar(partes[1]));
        labelAnyo.setText(partes[2]);
    }

    private String capitalizar(String texto) {

        if (texto == null || texto.isEmpty()) return texto;
        return texto.substring(0, 1).toUpperCase(LOCALE_ES) + texto.substring(1);

    }

    private void actions() {

        btnRegistrarJornada.setOnAction(actionEvent -> {
            LocalTime horaEntrada = LocalTime.now();
            LocalTime horaSalida = horaEntrada.plusHours(8);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
            Usuario u = DataSet.getUsuarioActual();


            String registro = String.format(
                    "Nombre: %s %s | Correo: %s | DNI: %s | Fecha: %s | Entrada: %s | Salida prevista: %s%n",
                    u.getNombre(), u.getApellidos(), u.getCorreo(), u.getDNI(),
                    LocalDate.now(), horaEntrada.format(fmt), horaSalida.format(fmt)
            );

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/org/example/appfichaje/fichero/horas.txt", true))) {
                bw.write(registro);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public void setUsuario(Usuario usuario) {
        nombreEmpleado.setText(usuario.getNombre());
        apellidoEmpleado.setText(usuario.getApellidos());
        puestoTrabajo.setText(usuario.getPuestoTrabajo());
        nombreProyecto.setText(usuario.getProyectoAsignado());
        txtFechaAlta.setText(String.valueOf(usuario.getFechaAlta()));
    }
}
