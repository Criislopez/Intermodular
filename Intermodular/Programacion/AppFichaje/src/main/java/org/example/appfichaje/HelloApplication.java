package org.example.appfichaje;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.appfichaje.controller.BaseController;

import java.io.IOException;

public class HelloApplication extends Application {

    public static Scene scene;
    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;

        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("inicio-view.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("perfil-view.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("crear-usuario-view.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("perfil-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-view.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("cambiar-datos-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 1200, 700);


        scene.getStylesheets().add(getClass().getResource("/estilos/estilo.css").toExternalForm());

        stage.setTitle("Bienvenido!");
        stage.setScene(scene);
        stage.show();

    }

    public static void setRoot(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("/org/example/appfichaje/" + fxml)
            );
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controller instanceof BaseController) {
                ((BaseController) controller).setStage(mainStage);
            }

            scene.setRoot(root);

        } catch (IOException e) {
            System.err.println("Error al cargar la vista: " + fxml);
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch();
    }
}
