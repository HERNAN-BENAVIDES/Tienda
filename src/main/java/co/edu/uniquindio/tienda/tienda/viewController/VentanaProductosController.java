package co.edu.uniquindio.tienda.tienda.viewController;

import co.edu.uniquindio.tienda.tienda.TiendaApplication;
import co.edu.uniquindio.tienda.tienda.controller.ModelFactoryController;
import co.edu.uniquindio.tienda.tienda.exception.ProductoException;
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
import java.util.ResourceBundle;

@SuppressWarnings("All")
@Data
public class VentanaProductosController implements Initializable {

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

    private Stage ventana;
    private TiendaApplication aplicacion;


    ObservableList<Producto> listProductos = FXCollections.observableArrayList();
    Producto productoSeleccionado;
    ModelFactoryController modelFactoryController;

    public VentanaProductosController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    @FXML
    /**
     * Inicializa la interfaz de usuario.
     * Este método se llama al iniciar la aplicación y configura el enlace de datos,
     * actualiza la lista de productos, establece el listener de selección y
     * inicializa los campos necesarios.
     */
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
        obtenerListaProductos();
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
    private void obtenerListaProductos() {
        listProductos.addAll(modelFactoryController.obtenerListaProductos());
    }

    /**
     * Configura el enlace de datos para las columnas de la tabla de productos.
     */
    private void initDataBinding() {
        tcNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        tcCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        tcPrecio.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPrecio())));
        tcInventario.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCantidadInventario())));
    }

    /**
     * Establece un listener para la selección de productos en la tabla.
     * Cuando se selecciona un producto, se muestra su información en los campos correspondientes.
     */
    private void listenerSelection() {
        tbProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            productoSeleccionado = newSelection;
            mostrarInformacionProducto(productoSeleccionado);
        });
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
     * Maneja el evento de edición de un producto.
     * Se llama cuando se hace clic en el botón de editar producto.
     * Llama al método para editar el producto seleccionado.
     *
     * @param mouseEvent El evento de ratón asociado al clic.
     */
    @FXML
    public void onEditarProducto(MouseEvent mouseEvent) {
        editarProducto();
    }

    /**
     * Edita el producto seleccionado.
     * Muestra alertas de error si no se selecciona ningún producto,
     * si los campos de texto están vacíos o si el producto no sufre cambios.
     * Llama al controlador para editar el producto.
     */
    private void editarProducto() {
        if (productoSeleccionado == null){
            Alertas.mostrarAlertaError("Debe seleccionar un producto");
            return;
        }

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

    /**
     * Maneja el evento de eliminación de un producto.
     * Se llama cuando se hace clic en el botón de eliminar producto.
     * Llama al método para eliminar el producto seleccionado.
     *
     * @param mouseEvent El evento de ratón asociado al clic.
     */
    @FXML
    public void onEliminarProducto(MouseEvent mouseEvent) {
        eliminarProducto();
    }

    /**
     * Elimina el producto seleccionado.
     * Muestra una alerta de error si no se selecciona ningún producto.
     * Llama al controlador para eliminar el producto.
     */
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

    /**
     * Maneja el evento de búsqueda de un producto.
     * Se llama cuando se hace clic en el botón de buscar producto.
     * Llama al método para buscar un producto por su código.
     *
     * @param mouseEvent El evento de ratón asociado al clic.
     */
    @FXML
    public void onBuscarProducto(MouseEvent mouseEvent) {
        buscarProducto();
    }

    /**
     * Busca un producto por su código.
     * Si el campo de búsqueda está vacío, actualiza la lista de productos.
     * Si se encuentra el producto, actualiza la lista de productos con el producto encontrado.
     * Muestra una alerta de error si el producto no se encuentra.
     */
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


    /**
     * Maneja el evento de creación de un nuevo producto.
     * Se llama cuando se hace clic en el botón de crear producto.
     * Llama al método para crear un nuevo producto.
     *
     * @param mouseEvent El evento de ratón asociado al clic.
     */
    @FXML
    public void onCrearProducto(MouseEvent mouseEvent) {
        crearProducto();
    }

    /**
     * Crea un nuevo producto y lo registra en la tienda.
     * Muestra una alerta de error si los campos de texto están vacíos.
     * Llama al controlador para agregar el nuevo producto a la tienda.
     */
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

    /**
     * Crea un nuevo objeto Producto utilizando los datos ingresados en los campos de texto.
     *
     * @return Un nuevo objeto Producto creado a partir de los datos de los campos de texto.
     */
    private Producto crearProductos() {
        return new Producto(txtNombre.getText(), txtCodigo.getText(), Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtCantidad.getText()));
    }

    /**
     * Valida si los campos de texto necesarios para crear un producto están llenos.
     *
     * @return true si algún campo está vacío, false si todos los campos están llenos.
     */
    private boolean validarCampo() {
        String nombre = txtNombre.getText();
        String codigo = txtCodigo.getText();
        String precio = txtPrecio.getText();

        return nombre.isEmpty() || codigo.isEmpty() || precio.isEmpty();
    }

    /**
     * Inicializa la interfaz de usuario al cargar la vista.
     * Este método se llama automáticamente cuando se carga la vista.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }

}

