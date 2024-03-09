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
        tablaClientes.getItems().clear();
        obtenerListaClientes();
        actualizarTabla();
    }

    private void actualizarTabla() {
        tablaClientes.setItems(listaClientes);
    }

    private void obtenerListaClientes() {
        listaClientes.addAll(modelFactoryController.obtenerListaClientes());
    }


    private void initDataBinding() {
        // Configura el enlace de datos para las columnas de la tabla
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colDireccion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNumIdentificacion()));
        colNumIdentificacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDireccion()));
    }

    private void listenerSelection() {
        tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            clienteSeleccionado = newSelection;
            mostrarInformacionCliente(clienteSeleccionado);
        });
    }

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


    @FXML
    void onCrearCliente(MouseEvent event) {
        crearCliente();
    }

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


    private Cliente crearClientes() {
        return new Cliente(txtNombre.getText(), txtNumIdentificacion.getText(), txtDireccion.getText());
    }

    private boolean validarCampo() {
        String nombre = txtNombre.getText();
        String numIdentificacion = txtNumIdentificacion.getText();
        String direccion = txtDireccion.getText();

        return nombre.isEmpty() || numIdentificacion.isEmpty() || direccion.isEmpty();
    }

    @FXML
    void onEditarCliente(MouseEvent event) {
        editarCliente();
    }

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


    @FXML
    void onEliminarCliente(MouseEvent event) {
        eliminarCliente();
    }

    private void eliminarCliente() {
        if(clienteSeleccionado == null){
            Alertas.mostrarAlertaError("Debe seleccionar un cliente");
            return;
        }

        try {
            if(modelFactoryController.eliminarCliente(clienteSeleccionado)){
                Alertas.mostrarAlertaInformacion("Se elimino correctamente el cliente");
                actualizarLista();
            }
        } catch (ClienteNoEncontradoException e) {
            Alertas.mostrarAlertaError(e.getMessage());
        }
    }

    @FXML
    public void onBuscarCliente(MouseEvent mouseEvent) {
        buscarCliente();
    }

    private void buscarCliente() {
        String id = txtBuscar.getText();

        if (id.isEmpty()){
            actualizarLista();
            return;
        }

        try {
            Cliente cliente = modelFactoryController.buscarCliente(id);

            if(cliente != null){
                listaClientes.clear();
                listaClientes.addAll(cliente);
                actualizarTabla();
            }
        } catch (ClienteNoEncontradoException e) {
            Alertas.mostrarAlertaError(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
