package co.edu.uniquindio.tienda.tienda.viewController;

import co.edu.uniquindio.tienda.tienda.controller.ModelFactoryController;
import co.edu.uniquindio.tienda.tienda.exception.ProductoException;
import co.edu.uniquindio.tienda.tienda.exception.ProductoNoEncontradoException;
import co.edu.uniquindio.tienda.tienda.model.Producto;
import co.edu.uniquindio.tienda.tienda.util.Alertas;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class VentanaProductosController {

    @FXML
    private ImageView btnBuscar;

    @FXML
    private ImageView btnCrear;

    @FXML
    private ImageView btnEditar;

    @FXML
    private ImageView btnEliminar;

    @FXML
    private TableView<Producto> tbProductos;

    @FXML
    private TableColumn<Producto, String> tcCodigo;

    @FXML
    private TableColumn<Producto, String> tcNombre;

    @FXML
    private TableColumn<Producto, String> tcPrecio;

    @FXML
    private TableColumn<Producto, String> tcInventario;

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


    ObservableList<Producto> listProductos = FXCollections.observableArrayList();
    Producto productoSeleccionado;
    ModelFactoryController modelFactoryController;

    public VentanaProductosController() {
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
        tcInventario.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCantidadInventario())));
    }

    private void listenerSelection() {
        tbProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            productoSeleccionado = newSelection;
            mostrarInformacionProducto(productoSeleccionado);
        });
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
    public void onEditarProducto(MouseEvent mouseEvent) {
        editarProducto();
    }

    private void editarProducto() {
        if (validarCampo()) {
            Alertas.mostrarAlertaError("Los campos de texto deben estar llenos");
            return;
        }

        Producto producto = crearProductos();
        if (producto.equals(productoSeleccionado)) {
            Alertas.mostrarAlertaError("El producto no sufrió ningún cambio");
            return;
        }

        try {
            if (modelFactoryController.editarProducto(producto, productoSeleccionado)) {
                Alertas.mostrarAlertaInformacion("Se editó correctamente el producto");
                actualizarLista();
            }
        } catch (ProductoException e) {
            Alertas.mostrarAlertaError(e.getMessage());
        }
    }

    @FXML
    public void onEliminarProducto(MouseEvent mouseEvent) {
        eliminarProducto();
    }

    private void eliminarProducto() {
        if (productoSeleccionado == null) {
            Alertas.mostrarAlertaError("Debe seleccionar un producto");
            return;
        }

        try {
            if (modelFactoryController.eliminarProducto(productoSeleccionado)) {
                Alertas.mostrarAlertaInformacion("Se eliminó correctamente el producto");
                actualizarLista();
            }
        } catch (ProductoNoEncontradoException e) {
            Alertas.mostrarAlertaError(e.getMessage());
        }
    }

    @FXML
    public void onBuscarProducto(MouseEvent mouseEvent) {
        buscarProducto();
    }

    private void buscarProducto() {
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

    @FXML
    public void onCrearProducto(MouseEvent mouseEvent) {
        crearProducto();
    }

    private void crearProducto() {
        if (validarCampo()) {
            Alertas.mostrarAlertaError("Los campos de texto deben estar llenos");
            return;
        }

        Producto producto = crearProductos();
        try {
            if (modelFactoryController.agregarProducto(producto)) {
                Alertas.mostrarAlertaInformacion("El producto se registró con éxito");
                actualizarLista();
            }
        } catch (ProductoException e) {
            Alertas.mostrarAlertaError(e.getMessage());
        }
    }

    private Producto crearProductos() {
        return new Producto(txtNombre.getText(), txtCodigo.getText(), Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtCantidad.getText()));
    }

    private boolean validarCampo() {
        String nombre = txtNombre.getText();
        String codigo = txtCodigo.getText();
        String precio = txtPrecio.getText();

        return nombre.isEmpty() || codigo.isEmpty() || precio.isEmpty();
    }

}

