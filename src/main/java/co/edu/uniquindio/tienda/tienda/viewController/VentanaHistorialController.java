package co.edu.uniquindio.tienda.tienda.viewController;
import co.edu.uniquindio.tienda.tienda.TiendaApplication;
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
public class VentanaHistorialController implements Initializable {

    @FXML
    private ImageView btnBuscar;

    @FXML
    private TableView<?> tbProductos;

    @FXML
    private TableColumn<?, ?> tcClienteID;

    @FXML
    private TableColumn<?, ?> tcCodigo;

    @FXML
    private TableColumn<?, ?> tcFecha;

    @FXML
    private TableColumn<?, ?> tcValorTotal;

    @FXML
    private TextField txtBuscar;

    private Stage ventana;
    private TiendaApplication aplicacion;

    @FXML
    void onBuscarProducto(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
