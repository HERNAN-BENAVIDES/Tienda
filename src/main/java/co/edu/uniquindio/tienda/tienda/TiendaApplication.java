package co.edu.uniquindio.tienda.tienda;

import co.edu.uniquindio.tienda.tienda.viewController.BarraOpcionesController;
import co.edu.uniquindio.tienda.tienda.viewController.VentanaInicioController;
import co.edu.uniquindio.tienda.tienda.viewController.VentanaProductosController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Data;

import java.io.IOException;
@Data

public class TiendaApplication extends Application {
    private Stage stage;
    private AnchorPane rootLayout;
    public VentanaInicioController ventanaInicioController;
    public BarraOpcionesController barraOpcionesController;
    public VentanaProductosController ventanaProductosController;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage=stage;
        this.stage.setTitle("Tienda Abuelita");
        this.stage.setResizable(false);
        mostrarVentanaInicio();
    }

    public void mostrarVentanaInicio() {
        try{
            FXMLLoader loader = new FXMLLoader(TiendaApplication.class.getResource("ventanas/VentanaInicio.fxml"));
            rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
            VentanaInicioController controlador = loader.getController();
            ventanaInicioController = loader.getController(); //obteniendo valor editable del controlador
            controlador.setAplicacion(this);
            controlador.setVentana(stage);
            controlador.mostrarPanelDerecho();
            controlador.mostrarBarraOpciones();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void mostrarVentanaHistorial(){
        ventanaInicioController.mostrarPanelDerechoHistorial();
    }

    public void mostrarVentanaProductos(){
        ventanaInicioController.mostrarPanelDerechoProductos();
    }

    public void mostrarVentanaCarrito(){
        ventanaInicioController.mostrarPanelDerechoCarrito();
    }

    public void mostrarVentanaVentas(){
        ventanaInicioController.mostrarPanelDerechoVentas();
    }

    public void mostrarVentanaClientes(){
        ventanaInicioController.mostrarPanelDerechoClientes();
    }
}