package co.edu.uniquindio.tienda.tienda.viewController;
import co.edu.uniquindio.tienda.tienda.TiendaApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Data;

import java.net.URL;
import java.util.ResourceBundle;
@Data
public class VentanaCarritoController implements Initializable {

    @FXML
    private TableView<?> tableCarrito;

    @FXML
    private TableColumn<?, ?> tcPrecio;

    @FXML
    private TableColumn<?, ?> tcProducto;

    @FXML
    private TextField txtValorTotal;

    private Stage ventana;
    private TiendaApplication aplicacion;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
