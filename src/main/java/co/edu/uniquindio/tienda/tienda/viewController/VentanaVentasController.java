package co.edu.uniquindio.tienda.tienda.viewController;

import co.edu.uniquindio.tienda.tienda.controller.ModelFactoryController;
import co.edu.uniquindio.tienda.tienda.model.Producto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class VentanaVentasController {

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


    ObservableList<Producto> listProductos = FXCollections.observableArrayList();
    Producto productoSeleccionado;
    ModelFactoryController modelFactoryController;

    int contador = 0;

    public VentanaVentasController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    @FXML
    void initialize() {
        initDataBinding();
        actualizarLista();
        listenerSelection();
        inicializarCampos();
    }

    private void inicializarCampos() {
        // Validador para el campo de código (solo permite números)
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtBuscar.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void actualizarLista() {
        tbProductos.getItems().clear();
        obtenerListaClientes();
        actualizarTabla();
    }

    private void actualizarTabla() {
        tbProductos.setItems(listProductos);
    }

    private void obtenerListaClientes() {
        listProductos.addAll(modelFactoryController.obtenerListaProductos());
    }


    private void initDataBinding() {
        // Configura el enlace de datos para las columnas de la tabla
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        tcPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));
        tcCantidad.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCantidadInventario())));
    }

    private void listenerSelection() {
        tbProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            productoSeleccionado = newSelection;
            mostrarInformacionProducto(productoSeleccionado);
            reiniciarContador();
        });
    }

    private void reiniciarContador() {
        contador = 0;
        txtCantidadProducto.setText(String.valueOf(contador));
    }

    private void mostrarInformacionProducto(Producto productoSeleccionado) {
        if (productoSeleccionado != null){
            txtNombre.setText(productoSeleccionado.getNombre());
            txtCodigo.setText(productoSeleccionado.getCodigo());
            txtPrecio.setText(String.valueOf(productoSeleccionado.getPrecio()));
            txtCantidad.setText(String.valueOf(productoSeleccionado.getCantidadInventario()));
        }
    }


    @FXML
    public void onBuscarProducto(MouseEvent mouseEvent) {
    }

    public void onAgregar(MouseEvent mouseEvent) {
        agregarElemento();
    }

    private void agregarElemento() {
        if(productoSeleccionado == null){
            return;
        }

        if(contador < productoSeleccionado.getCantidadInventario()){
            contador++;
        }
        txtCantidadProducto.setText(String.valueOf(contador));
    }

    public void onDisminuir(MouseEvent mouseEvent) {
        quitarElemento();
    }

    private void quitarElemento() {
        if (contador > 0 && productoSeleccionado != null) {
            contador--;
        }
        txtCantidadProducto.setText(String.valueOf(contador));
    }
}
