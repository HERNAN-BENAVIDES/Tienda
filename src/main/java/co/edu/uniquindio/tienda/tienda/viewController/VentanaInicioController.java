package co.edu.uniquindio.tienda.tienda.viewController;
import co.edu.uniquindio.tienda.tienda.TiendaApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;
@Data
public class VentanaInicioController implements Initializable {

    @FXML
    private AnchorPane barraOpciones;

    @FXML
    private AnchorPane panelDerecho;

    @FXML
    private AnchorPane panelPrincipal;

    private TiendaApplication aplicacion;
    private Stage ventana;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void mostrarBarraOpciones() {
        try {
            barraOpciones.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(TiendaApplication.class.getResource("ventanas/BarraOpciones.fxml"));
            Node node = loader.load();
            barraOpciones.getChildren().add(node);
            BarraOpcionesController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarPanelDerecho() {
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(TiendaApplication.class.getResource("ventanas/VentanaProductos.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaProductosController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarPanelDerechoHistorial() {
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(TiendaApplication.class.getResource("ventanas/VentanaHistorial.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaHistorialController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setVentana(ventana);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarPanelDerechoVentas() {
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(TiendaApplication.class.getResource("ventanas/VentanaVentas.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaVentasController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setVentana(ventana);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarPanelDerechoClientes() {
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(TiendaApplication.class.getResource("ventanas/VentanaClientes.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaClientesController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setVentana(ventana);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarPanelDerechoCarrito() {
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(TiendaApplication.class.getResource("ventanas/VentanaCarrito.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaCarritoController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setVentana(ventana);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarPanelDerechoProductos(){
        try {
            panelDerecho.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(TiendaApplication.class.getResource("ventanas/VentanaProductos.fxml"));
            Node node = loader.load();
            panelDerecho.getChildren().add(node);
            VentanaProductosController controlador = loader.getController();
            controlador.setAplicacion(this.aplicacion);
            controlador.setVentana(ventana);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}