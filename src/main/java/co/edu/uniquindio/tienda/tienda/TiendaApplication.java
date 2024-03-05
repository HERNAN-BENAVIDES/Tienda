package co.edu.uniquindio.tienda.tienda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TiendaApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TiendaApplication.class.getResource("ventanas/VentanaClientes.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Subasta UQ");
        stage.setScene(scene);
        stage.show();
    }
}