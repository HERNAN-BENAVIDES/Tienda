package co.edu.uniquindio.tienda.tienda.viewController;

import co.edu.uniquindio.tienda.tienda.TiendaApplication;
import co.edu.uniquindio.tienda.tienda.controller.ModelFactoryController;
import co.edu.uniquindio.tienda.tienda.model.Cliente;
import co.edu.uniquindio.tienda.tienda.model.DetalleVenta;
import co.edu.uniquindio.tienda.tienda.model.Producto;
import co.edu.uniquindio.tienda.tienda.model.Venta;
import co.edu.uniquindio.tienda.tienda.util.Alertas;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("All")
@Data
public class VentanaCarritoController implements Initializable {

    @FXML
    private Button btnAceptar;

    @FXML
    private Button btnRechazar;

    @FXML
    private ComboBox<String> cbSelecCliente;

    @FXML
    private TableView<Producto> tableCarrito;

    @FXML
    private TableColumn<Producto, String> tcPrecio;

    @FXML
    private TableColumn<Producto, String> tcProducto;

    @FXML
    private TableColumn<Producto, String> tcCantidad;

    @FXML
    private TableColumn<Producto, String> tcTotal;

    @FXML
    private TextField txtValorTotal;

    @FXML
    private TextField txtTotal;

    private Stage ventana;
    private TiendaApplication aplicacion;
    private ModelFactoryController modelFactoryController;
    private ObservableList<String> listaClientesNombres = FXCollections.observableArrayList();
    private List<Cliente> listClientes = new ArrayList<>();
    private HashMap<String, Integer> productosCarrito = new HashMap<>();
    private ObservableList<Producto> listaProductos = FXCollections.observableArrayList();

    public VentanaCarritoController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    /**
     * Inicializa la vista al cargar.
     * Llama a los métodos para inicializar el enlace de datos, obtener la lista de clientes y productos, actualizar la tabla y calcular el total de la venta.
     *
     * @param url            la ubicación relativa del recurso raíz al que se resuelve el argumento {@code location}, o {@code null} si no se ha resuelto
     * @param resourceBundle el recurso del paquete para localizar los objetos de cadena, o {@code null} si no se necesita
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDataBinding();
        obtenerListaClientes();
        obtenerProductos();
        actualizarTabla();
        calcularTotalVenta();
    }

    /**
     * Calcula el total de la venta sumando el valor total de cada producto en el carrito.
     * Actualiza el campo de texto de total en la interfaz con el valor calculado.
     */
    private void calcularTotalVenta() {
        double total = 0.0;
        for (Producto producto : listaProductos) {
            double valorTotalProducto = Double.parseDouble(calcularTotal(producto));
            total += valorTotalProducto;
        }
        txtTotal.setText(String.valueOf(total));
    }

    /**
     * Obtiene la lista de clientes del controlador de modelo y la configura en el ComboBox de selección de cliente.
     * También configura un Listener para actualizar el ComboBox cuando se muestra.
     */
    private void obtenerListaClientes() {
        listClientes = modelFactoryController.obtenerListaClientes();
        listaClientesNombres.addAll(modelFactoryController.obtenerListaClientesNombres());
        cbSelecCliente.setItems(listaClientesNombres);
        cbSelecCliente.getSelectionModel().selectFirst();
        cbSelecCliente.setOnShowing(event -> actualizarComboBox());
    }

    /**
     * Actualiza el ComboBox de selección de cliente con la lista de clientes actualizada.
     */
    private void actualizarComboBox() {
        listaClientesNombres.clear();
        listaClientesNombres.addAll(modelFactoryController.obtenerListaClientesNombres());
        cbSelecCliente.setItems(listaClientesNombres);
        cbSelecCliente.getSelectionModel().selectFirst();
    }

    /**
     * Obtiene los productos del carrito del controlador de modelo y los configura en la tabla del carrito.
     */
    public void obtenerProductos() {
        productosCarrito = modelFactoryController.obtenerCarrito();
        obtenerProductosCarrito();
    }

    /**
     * Actualiza la tabla del carrito con la lista de productos actualizada.
     */
    private void actualizarTabla() {
        tableCarrito.setItems(listaProductos);
    }

    /**
     * Configura el enlace de datos para las columnas de la tabla del carrito.
     */
    private void initDataBinding() {
        tcProducto.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));
        tcCantidad.setCellValueFactory(cellData -> new SimpleStringProperty(obtenerCantidad(cellData.getValue())));
        tcTotal.setCellValueFactory(cellData -> new SimpleStringProperty(calcularTotal(cellData.getValue())));
    }

    /**
     * Calcula el valor total de un producto multiplicando su precio por la cantidad en el carrito.
     *
     * @param producto el producto para calcular su valor total
     * @return el valor total del producto como una cadena
     */
    private String calcularTotal(Producto producto) {
        String codigoProducto = producto.getCodigo();
        int cantidad = productosCarrito.get(codigoProducto); // Obtiene la cantidad asociada al código del producto en el carrito
        double precio = producto.getPrecio(); // Obtiene el precio del producto
        double total = cantidad * precio; // Calcula el total multiplicando la cantidad por el precio
        return String.valueOf(total); // Devuelve el total como una cadena
    }

    /**
     * Obtiene la cantidad de un producto en el carrito.
     *
     * @param producto el producto para obtener su cantidad
     * @return la cantidad del producto como una cadena
     */
    private String obtenerCantidad(Producto producto) {
        String codigoProducto = producto.getCodigo(); // Obtén el código del producto
        int cantidad = productosCarrito.get(codigoProducto); // Obtiene la cantidad asociada al código del producto en el carrito
        return String.valueOf(cantidad); // Devuelve la cantidad como una cadena
    }



    /**
     * Obtiene los productos del carrito del controlador de modelo y los agrega a la lista de productos del carrito.
     * Utiliza el código de cada producto en el carrito para obtener la información completa del producto y agregarlo a la lista de productos del carrito.
     */
    private void obtenerProductosCarrito() {
        for(String codigo: productosCarrito.keySet()){
            listaProductos.addAll(modelFactoryController.obtenerProductoPorCodigo(codigo));
        }
    }

    /**
     * Realiza la compra si hay productos en el carrito y se ha seleccionado un cliente.
     * Crea una venta con los detalles de venta obtenidos del carrito y la información del cliente seleccionado.
     * Agrega la venta al historial de ventas, vacía el carrito, actualiza el inventario y limpia los campos.
     * Muestra una alerta de éxito si la venta se agrega correctamente al historial.
     * Muestra una alerta de error si ocurre algún problema al agregar la venta al historial.
     */
    @FXML
    void realizarCompra(ActionEvent event) {
        if (!tableCarrito.getItems().isEmpty() && cbSelecCliente.getValue() != null && !cbSelecCliente.getValue().equals(cbSelecCliente.getItems().get(0))) {
            List<DetalleVenta> detallesVenta = obtenerDetallesVenta();
            Venta venta = new Venta(generarCodigo(), LocalDate.now(), Double.parseDouble(txtTotal.getText()), obtenerCliente(), detallesVenta);
            if(modelFactoryController.agregarVenta(venta)){
                Alertas.mostrarAlertaInformacion("Se agregó la venta al historial");
                modelFactoryController.vaciarCarrito();
                modelFactoryController.actulizarInventario(detallesVenta);
                limpiarCampos();
            } else {
                Alertas.mostrarAlertaError("Error al añadir la venta al historial");
            }
        } else {
            Alertas.mostrarAlertaError("Debe seleccionar un cliente");
        }
    }

    /**
     * Limpia los campos después de realizar la compra.
     * Limpia la lista de productos del carrito, la tabla que muestra los productos, establece la posición del ComboBox al índice 0 y limpia el TextField que muestra el total.
     */
    private void limpiarCampos() {
        listaProductos.clear();
        tableCarrito.getItems().clear();
        cbSelecCliente.getSelectionModel().selectFirst();
        txtTotal.clear();
    }

    /**
     * Obtiene el cliente seleccionado del ComboBox de selección de cliente.
     * Busca el cliente en la lista de clientes utilizando el nombre seleccionado en el ComboBox.
     * @return el cliente seleccionado, o null si no se encuentra
     */
    private Cliente obtenerCliente() {
        String nombreCliente = cbSelecCliente.getValue();
        for (Cliente cliente : listClientes) {
            if (cliente.getNombre().equals(nombreCliente)) {
                return cliente;
            }
        }
        return null; // Si no se encuentra el cliente seleccionado
    }

    /**
     * Obtiene los detalles de venta de los productos en el carrito.
     * Recorre la lista de productos del carrito, obtiene la cantidad y el subtotal de cada producto y crea un DetalleVenta.
     * @return la lista de detalles de venta de los productos en el carrito
     */
    private List<DetalleVenta> obtenerDetallesVenta() {
        List<DetalleVenta> detallesVenta = new ArrayList<>();
        for (Producto producto : listaProductos) {
            String codigoProducto = producto.getCodigo();
            int cantidad = productosCarrito.get(codigoProducto); // Obtiene la cantidad asociada al código del producto en el carrito
            double precio = producto.getPrecio(); // Obtiene el precio del producto
            double subTotal = cantidad * precio; // Calcula el subtotal multiplicando la cantidad por el precio
            DetalleVenta detalleVenta = new DetalleVenta(cantidad, producto, subTotal);
            detallesVenta.add(detalleVenta);
        }
        return detallesVenta;
    }

    /**
     * Genera un código alfanumérico aleatorio de longitud 4 para identificar una venta.
     * @return el código generado
     */
    private String generarCodigo() {
        StringBuilder codigo = new StringBuilder();
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < 4; i++) {
            int index = (int) (Math.random() * letras.length());
            codigo.append(letras.charAt(index));
        }
        return codigo.toString();
    }

    /**
     * Vacía el carrito de compras.
     * Llama al método correspondiente en el controlador de modelo y muestra una alerta informativa.
     */
    @FXML
    void rechazarCarrito(ActionEvent event) {
        modelFactoryController.vaciarCarrito();
        Alertas.mostrarAlertaInformacion("Se eliminaron los productos del carrito");
    }

}
