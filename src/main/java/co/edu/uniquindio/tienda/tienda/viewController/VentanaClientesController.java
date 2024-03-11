package co.edu.uniquindio.tienda.tienda.viewController;

import co.edu.uniquindio.tienda.tienda.TiendaApplication;
import co.edu.uniquindio.tienda.tienda.controller.ModelFactoryController;
import co.edu.uniquindio.tienda.tienda.exception.ClienteException;
import co.edu.uniquindio.tienda.tienda.exception.ClienteNoEncontradoException;
import co.edu.uniquindio.tienda.tienda.model.Cliente;
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
public class VentanaClientesController implements Initializable {

    ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    Cliente clienteSeleccionado;
    ModelFactoryController modelFactoryController;

    @FXML
    private ImageView btnBuscar;

    @FXML
    private ImageView btnCrear;

    @FXML
    private ImageView btnEditar;

    @FXML
    private ImageView btnEliminar;

    @FXML
    private TableView<Cliente> tablaClientes;

    @FXML
    private TableColumn<Cliente, String> colDireccion;

    @FXML
    private TableColumn<Cliente, String> colNumIdentificacion;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtNumIdentificacion;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtNombre;
    private Stage ventana;
    private TiendaApplication aplicacion;


    public VentanaClientesController() {
        modelFactoryController = ModelFactoryController.getInstance();
    }

    /**
     * Inicializa la vista al cargar.
     * Configura el enlace de datos, actualiza la lista de clientes,
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
     * Actualiza la lista de clientes en la tabla de la interfaz de usuario.
     */
    private void actualizarLista() {
        tablaClientes.getItems().clear();
        obtenerListaClientes();
        actualizarTabla();
    }

    /**
     * Actualiza la tabla de clientes en la interfaz de usuario.
     */
    private void actualizarTabla() {
        tablaClientes.setItems(listaClientes);
    }

    /**
     * Obtiene la lista de clientes desde el controlador y la agrega a la lista observable.
     */
    private void obtenerListaClientes() {
        listaClientes.addAll(modelFactoryController.obtenerListaClientes());
    }

    /**
     * Configura el enlace de datos para las columnas de la tabla de clientes.
     */
    private void initDataBinding() {
        // Configura el enlace de datos para las columnas de la tabla
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumIdentificacion()));
        colNumIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
    }

    /**
     * Establece un listener para la selección de clientes en la tabla.
     * Cuando se selecciona un cliente, se muestra su información en los campos correspondientes.
     */
    private void listenerSelection() {
        tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            clienteSeleccionado = newSelection;
            mostrarInformacionCliente(clienteSeleccionado);
        });
    }

    /**
     * Muestra la información del cliente seleccionado en los campos de la interfaz de usuario.
     *
     * @param clienteSeleccionado El cliente seleccionado.
     */
    private void mostrarInformacionCliente(Cliente clienteSeleccionado) {
        if (clienteSeleccionado != null) {
            txtNombre.setText(clienteSeleccionado.getNombre());
            txtDireccion.setText(clienteSeleccionado.getDireccion());
            txtNumIdentificacion.setText(clienteSeleccionado.getNumIdentificacion());
        } else {
            txtNombre.setText("");
            txtDireccion.setText("");
            txtNumIdentificacion.setText("");
        }
    }


    /**
     * Maneja el evento de crear un nuevo cliente.
     * Se llama cuando se hace clic en el botón de crear cliente.
     * Llama al método para crear un nuevo cliente.
     *
     * @param event El evento de ratón asociado al clic.
     */
    @FXML
    void onCrearCliente(MouseEvent event) {
        crearCliente();
    }

    /**
     * Crea un nuevo cliente.
     * Verifica si los campos de texto están llenos.
     * Si están llenos, crea un nuevo cliente y lo agrega al modelo.
     * Muestra una alerta de éxito si el cliente se registra correctamente.
     * En caso de error, muestra una alerta de error con el mensaje correspondiente.
     */
    private void crearCliente() {
        if (validarCampo()) {
            Alertas.mostrarAlertaError("Los campos de texto deben estar llenos");
            return;
        }

        Cliente cliente = crearClientes();
        try {
            if (modelFactoryController.agregarCliente(cliente)) {
                Alertas.mostrarAlertaInformacion("El cliente se registró con éxito");
                actualizarLista();
            }
        } catch (ClienteException e) {
            Alertas.mostrarAlertaError(e.getMessage());
        }
    }

    /**
     * Crea un nuevo objeto Cliente a partir de los valores ingresados en los campos de texto.
     *
     * @return El nuevo objeto Cliente creado.
     */
    private Cliente crearClientes() {
        return new Cliente(txtNombre.getText(), txtNumIdentificacion.getText(), txtDireccion.getText());
    }

    /**
     * Verifica si los campos de texto están vacíos.
     *
     * @return true si algún campo de texto está vacío, false de lo contrario.
     */
    private boolean validarCampo() {
        String nombre = txtNombre.getText();
        String numIdentificacion = txtNumIdentificacion.getText();
        String direccion = txtDireccion.getText();

        return nombre.isEmpty() || numIdentificacion.isEmpty() || direccion.isEmpty();
    }

    /**
     * Maneja el evento de editar un cliente.
     * Se llama cuando se hace clic en el botón de editar cliente.
     * Llama al método para editar un cliente.
     *
     * @param event El evento de ratón asociado al clic.
     */
    @FXML
    void onEditarCliente(MouseEvent event) {
        editarCliente();
    }

    /**
     * Edita un cliente existente.
     * Verifica si los campos de texto están llenos.
     * Si están llenos y el cliente ha sido modificado, llama al método para editar el cliente.
     * Muestra una alerta de éxito si el cliente se edita correctamente.
     * En caso de error, muestra una alerta de error con el mensaje correspondiente.
     */
    private void editarCliente() {
        if (validarCampo()) {
            Alertas.mostrarAlertaError("Los campos de texto deben estar llenos");
            return;
        }

        Cliente cliente = crearClientes();
        if (cliente.equals(clienteSeleccionado)) {
            Alertas.mostrarAlertaError("El cliente no sufrió ningún cambio");
            return;
        }

        try {
            if (modelFactoryController.editarCliente(cliente, clienteSeleccionado)) {
                Alertas.mostrarAlertaInformacion("Se editó correctamente el cliente");
                actualizarLista();
            }
        } catch (ClienteException e) {
            Alertas.mostrarAlertaError(e.getMessage());
        }
    }


    /**
     * Maneja el evento de eliminar un cliente.
     * Se llama cuando se hace clic en el botón de eliminar cliente.
     * Llama al método para eliminar un cliente.
     *
     * @param event El evento de ratón asociado al clic.
     */
    @FXML
    void onEliminarCliente(MouseEvent event) {
        eliminarCliente();
    }

    /**
     * Elimina un cliente existente.
     * Verifica si se ha seleccionado un cliente.
     * Si se ha seleccionado, llama al método para eliminar el cliente.
     * Muestra una alerta de éxito si el cliente se elimina correctamente.
     * En caso de error, muestra una alerta de error con el mensaje correspondiente.
     */
    private void eliminarCliente() {
        if (clienteSeleccionado == null) {
            Alertas.mostrarAlertaError("Debe seleccionar un cliente");
            return;
        }

        try {
            if (modelFactoryController.eliminarCliente(clienteSeleccionado)) {
                Alertas.mostrarAlertaInformacion("Se eliminó correctamente el cliente");
                actualizarLista();
            }
        } catch (ClienteNoEncontradoException e) {
            Alertas.mostrarAlertaError(e.getMessage());
        }
    }

    /**
     * Maneja el evento de buscar un cliente.
     * Se llama cuando se hace clic en el botón de buscar cliente.
     * Llama al método para buscar un cliente.
     *
     * @param mouseEvent El evento de ratón asociado al clic.
     */
    @FXML
    public void onBuscarCliente(MouseEvent mouseEvent) {
        buscarCliente();
    }

    /**
     * Busca un cliente por su ID.
     * Si no se ingresa un ID, actualiza la lista de clientes.
     * Si se encuentra un cliente con el ID especificado, muestra la lista de clientes con ese cliente.
     * En caso de no encontrar ningún cliente con el ID especificado, muestra una alerta de error.
     */
    private void buscarCliente() {
        String id = txtBuscar.getText();

        if (id.isEmpty()) {
            actualizarLista();
            return;
        }

        try {
            Cliente cliente = modelFactoryController.buscarCliente(id);

            if (cliente != null) {
                listaClientes.clear();
                listaClientes.add(cliente);
                actualizarTabla();
            }
        } catch (ClienteNoEncontradoException e) {
            Alertas.mostrarAlertaError(e.getMessage());
        }
    }

    /**
     * Inicializa la vista al cargar.
     * Llama a los métodos para inicializar el enlace de datos y actualizar la lista de clientes.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialize();
    }

}
