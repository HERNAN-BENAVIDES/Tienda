package co.edu.uniquindio.tienda.tienda.viewController;

import co.edu.uniquindio.tienda.tienda.TiendaApplication;
import co.edu.uniquindio.tienda.tienda.controller.ModelFactoryController;
import co.edu.uniquindio.tienda.tienda.exception.ProductoNoEncontradoException;
import co.edu.uniquindio.tienda.tienda.model.Producto;
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
import java.util.HashMap;
import java.util.ResourceBundle;

@SuppressWarnings("All")
@Data
public class VentanaVentasController implements Initializable {

    @FXML
    private ImageView btnAgregar;

    @FXML
    private ImageView btnDisminuir;

    @FXML
    private ImageView btnBuscar;

    @FXML
    private TableView<Producto> tbProductos;

    @FXML
    private TableColumn<Producto, String> tcCantidad;

    @FXML
    private TableColumn<Producto, String> tcCodigo;

    @FXML
    private TableColumn<Producto, String> tcNombre;

    @FXML
    private TableColumn<Producto, String> tcPrecio;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtCantidadProducto;

    /**
     * Controlador para la ventana de ventas.
     */
    private Stage ventana;
    private TiendaApplication aplicacion;

    ObservableList<Producto> listProductos = FXCollections.observableArrayList();
    Producto productoSeleccionado;
    ModelFactoryController modelFactoryController;

    int contador = 0;

    /**
     * Constructor del controlador.
     * Inicializa la instancia del controlador de modelo.
     */
    public VentanaVentasController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    /**
     * Inicializa la interfaz de usuario.
     * Configura el enlace de datos, actualiza la lista de productos,
     * establece el listener de selección y inicializa los campos necesarios.
     */
    @FXML
    void initialize() {
        initDataBinding();
        actualizarLista();
        listenerSelection();
        inicializarCampos();
    }

    /**
     * Inicializa los campos de la interfaz de usuario.
     * Establece un validador para el campo de búsqueda que permite solo números.
     */
    private void inicializarCampos() {
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtBuscar.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * Actualiza la lista de productos en la tabla de la interfaz de usuario.
     */
    private void actualizarLista() {
        tbProductos.getItems().clear();
        obtenerListaClientes();
        actualizarTabla();
    }

    /**
     * Actualiza la tabla de productos en la interfaz de usuario.
     */
    private void actualizarTabla() {
        tbProductos.setItems(listProductos);
    }

    /**
     * Obtiene la lista de productos desde el controlador y la agrega a la lista observable.
     */
    private void obtenerListaClientes() {
        listProductos.addAll(modelFactoryController.obtenerListaProductos());
    }

    /**
     * Configura el enlace de datos para las columnas de la tabla de productos.
     */
    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        tcPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));
        tcCantidad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCantidadInventario())));
    }

    /**
     * Establece un listener para la selección de productos en la tabla.
     * Cuando se selecciona un producto, se muestra su información en los campos correspondientes
     * y se reinicia el contador.
     */
    private void listenerSelection() {
        tbProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            productoSeleccionado = newSelection;
            mostrarInformacionProducto(productoSeleccionado);
            reiniciarContador();
        });
    }

    /**
     * Reinicia el contador de productos seleccionados.
     */
    private void reiniciarContador() {
        contador = 0;
    }


    /**
     * Muestra la información del producto seleccionado en los campos de la interfaz de usuario.
     *
     * @param productoSeleccionado El producto seleccionado.
     */
    private void mostrarInformacionProducto(Producto productoSeleccionado) {
        if (productoSeleccionado != null){
            txtNombre.setText(productoSeleccionado.getNombre());
            txtCodigo.setText(productoSeleccionado.getCodigo());
            txtPrecio.setText(String.valueOf(productoSeleccionado.getPrecio()));
            txtCantidad.setText(String.valueOf(productoSeleccionado.getCantidadInventario()));
        }
    }

    /**
     * Maneja el evento de búsqueda de un producto.
     * Se llama cuando se hace clic en el botón de buscar producto.
     * Llama al método para buscar un producto por su código.
     *
     * @param mouseEvent El evento de ratón asociado al clic.
     */
    @FXML
    public void onBuscarProducto(MouseEvent mouseEvent) {
        String codigo = txtBuscar.getText();

        if (codigo.isEmpty()) {
            actualizarLista();
            return;
        }

        try {
            Producto producto = modelFactoryController.buscarProducto(codigo);

            if (producto != null) {
                listProductos.clear();
                listProductos.add(producto);
                actualizarTabla();
            }
        } catch (ProductoNoEncontradoException e) {
            Alertas.mostrarAlertaError(e.getMessage());
        }
    }

    /**
     * Maneja el evento de agregar un elemento al carrito.
     * Se llama cuando se hace clic en el botón de agregar.
     * Llama al método para agregar un elemento al carrito.
     *
     * @param mouseEvent El evento de ratón asociado al clic.
     */
    public void onAgregar(MouseEvent mouseEvent) {
        agregarElemento();
    }

    /**
     * Agrega un elemento al carrito.
     * Aumenta el contador si es posible agregar más elementos.
     * Actualiza el campo de cantidad en la interfaz de usuario.
     */
    private void agregarElemento() {
        if(productoSeleccionado == null){
            return;
        }

        if(contador < productoSeleccionado.getCantidadInventario()){
            contador++;
        }
        txtCantidadProducto.setText(String.valueOf(contador));
    }

    /**
     * Maneja el evento de disminuir un elemento del carrito.
     * Se llama cuando se hace clic en el botón de disminuir.
     * Llama al método para disminuir un elemento del carrito.
     *
     * @param mouseEvent El evento de ratón asociado al clic.
     */
    public void onDisminuir(MouseEvent mouseEvent) {
        quitarElemento();
    }

    /**
     * Disminuye un elemento del carrito.
     * Disminuye el contador si es posible disminuir más elementos.
     * Actualiza el campo de cantidad en la interfaz de usuario.
     */
    private void quitarElemento() {
        if (contador > 0 && productoSeleccionado != null) {
            contador--;
        }
        txtCantidadProducto.setText(String.valueOf(contador));
    }
    /**
     * Inicializa la vista al cargar la URL.
     * Llama al método de inicialización del controlador.
     *
     * @param url             La URL de la ubicación utilizada para inicializar el controlador.
     * @param resourceBundle El ResourceBundle que se puede usar para localizar objetos de texto.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }

    /**
     * Maneja el evento de añadir un producto al carrito.
     * Se llama cuando se hace clic en el botón de añadir al carrito.
     * Llama al método para añadir el producto al carrito.
     *
     * @param mouseEvent El evento de ratón asociado al clic.
     */
    public void onAniadirCarrito(MouseEvent mouseEvent) {
        aniadirCarrito();
    }

    /**
     * Añade un producto al carrito.
     * Muestra una alerta de información si la cantidad es igual a cero,
     * de lo contrario, agrega el producto al carrito y muestra una alerta de éxito.
     */
    private void aniadirCarrito() {
        if(txtCantidadProducto.getText().equals("0")){
            Alertas.mostrarAlertaInformacion("La cantidad debe ser distinta de cero");
        }else{
            String codigoProducto = productoSeleccionado.getCodigo();
            int cantidad = Integer.parseInt(txtCantidadProducto.getText());
            modelFactoryController.agregarProductoCarrito(codigoProducto,cantidad);
            Alertas.mostrarAlertaInformacion("Se agregó el producto al carrito");
        }
    }

}
