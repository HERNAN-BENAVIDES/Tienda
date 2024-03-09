package co.edu.uniquindio.tienda.tienda.viewController;

import co.edu.uniquindio.tienda.tienda.TiendaApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;
@Data
public class BarraOpcionesController implements Initializable {
    @FXML
    private Button btnCarrito;

    @FXML
    private Button btnClientes;

    @FXML
    private Button btnHistorial;

    @FXML
    private Button btnProductos;

    @FXML
    private Button btnVentas;

    private TiendaApplication aplicacion;
    private Stage ventana;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void abrirVentanaClientes(){
        aplicacion.mostrarVentanaClientes();
    }
    public void abrirVentanaVentas(){
        aplicacion.mostrarVentanaVentas();
    }
    public void abrirVentanaProductos(){
        aplicacion.mostrarVentanaProductos();
    }
    public void abrirVentanaHistorial(){
        aplicacion.mostrarVentanaHistorial();
    }
    public void abrirVentanaCarrito(){
        aplicacion.mostrarVentanaCarrito();
    }
}
