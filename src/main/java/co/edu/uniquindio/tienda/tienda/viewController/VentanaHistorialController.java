package co.edu.uniquindio.tienda.tienda.viewController;
import co.edu.uniquindio.tienda.tienda.TiendaApplication;
import co.edu.uniquindio.tienda.tienda.controller.ModelFactoryController;
import co.edu.uniquindio.tienda.tienda.exception.ProductoNoEncontradoException;
import co.edu.uniquindio.tienda.tienda.model.Producto;
import co.edu.uniquindio.tienda.tienda.model.Venta;
import co.edu.uniquindio.tienda.tienda.util.Alertas;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;
@SuppressWarnings("All")
@Data
public class VentanaHistorialController implements Initializable {

    @FXML
    private ImageView btnBuscar;

    @FXML
    private TableView<Venta> tbProductos;

    @FXML
    private TableColumn<Venta, String> tcClienteID;

    @FXML
    private TableColumn<Venta, String> tcCodigo;

    @FXML
    private TableColumn<Venta, String> tcFecha;

    @FXML
    private TableColumn<Venta, String> tcValorTotal;

    @FXML
    private TextField txtBuscar;

    private Stage ventana;
    private TiendaApplication aplicacion;
    ModelFactoryController modelFactoryController;
    ObservableList<Venta> listVentas = FXCollections.observableArrayList();



    public VentanaHistorialController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    /**
     * Maneja el evento de buscar una venta por su código.
     * Se llama cuando se hace clic en el botón de buscar venta.
     * Llama al método para buscar una venta por su código.
     *
     * @param event El evento de ratón asociado al clic.
     */
    @FXML
    void onBuscarVenta(MouseEvent event) {
        buscarVenta();
    }

    /**
     * Busca una venta por su código.
     * Si el campo de búsqueda está vacío, actualiza la lista de ventas.
     * Si se encuentra una venta con el código ingresado, actualiza la tabla con esa venta.
     * De lo contrario, muestra una alerta de información.
     */
    private void buscarVenta() {
        String codigo = txtBuscar.getText();

        if (codigo.isEmpty()) {
            actualizarLista();
            return;
        }

        Venta venta = modelFactoryController.buscarVenta(codigo);

        if (venta != null) {
            listVentas.clear();
            listVentas.add(venta);
            actualizarTabla();
            return;
        }
        Alertas.mostrarAlertaInformacion("No se encontró una venta con el código ingresado");
        actualizarLista();
    }

    /**
     * Inicializa la vista al cargar la URL.
     * Configura el enlace de datos y actualiza la lista de ventas.
     *
     * @param url             La URL de la ubicación utilizada para inicializar el controlador.
     * @param resourceBundle El ResourceBundle que se puede usar para localizar objetos de texto.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDataBinding();
        actualizarLista();
    }

    /**
     * Actualiza la lista de ventas en la tabla de la interfaz de usuario.
     */
    private void actualizarLista() {
        tbProductos.getItems().clear();
        obtenerListaClientes();
        actualizarTabla();
    }

    /**
     * Actualiza la tabla de ventas en la interfaz de usuario.
     */
    private void actualizarTabla() {
        tbProductos.setItems(listVentas);
    }

    /**
     * Obtiene la lista de ventas desde el controlador y la agrega a la lista observable.
     */
    private void obtenerListaClientes() {
        listVentas.addAll(modelFactoryController.obtenerListaVentas());
    }

    /**
     * Configura el enlace de datos para las columnas de la tabla de ventas.
     */
    private void initDataBinding() {
        // Configura el enlace de datos para las columnas de la tabla
        tcFecha.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFecha())));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        tcValorTotal.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTotal())));
        tcClienteID.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCliente().getNumIdentificacion())));
    }

}
