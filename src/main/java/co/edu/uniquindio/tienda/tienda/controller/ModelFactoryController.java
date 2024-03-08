package co.edu.uniquindio.tienda.tienda.controller;




import co.edu.uniquindio.tienda.tienda.exception.ClienteException;
import co.edu.uniquindio.tienda.tienda.exception.ClienteNoEncontradoException;
import co.edu.uniquindio.tienda.tienda.exception.ProductoException;
import co.edu.uniquindio.tienda.tienda.exception.ProductoNoEncontradoException;
import co.edu.uniquindio.tienda.tienda.model.Cliente;
import co.edu.uniquindio.tienda.tienda.model.Producto;
import co.edu.uniquindio.tienda.tienda.model.Tienda;
import co.edu.uniquindio.tienda.tienda.util.TiendaUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class ModelFactoryController {

    private Tienda tienda;

    public ModelFactoryController() {
        cargarDatosPrueba();
    }

    private void cargarDatosPrueba() {
        tienda = TiendaUtils.inicializarDatos();
    }

    /*
    -----------------------------------------------------------------------------------------------------------
    --------------------------------------GET INSTANCE---------------------------------------------------------
    */
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }
    private static class SingletonHolder {
    private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }
    /*
    -----------------------------------------------------------------------------------------------------------
    -----------------------------------------------------------------------------------------------------------
    */

    public List<Cliente> obtenerListaClientes() {
        return new ArrayList<>(tienda.getListClientes().values());
    }
    public boolean agregarCliente(Cliente cliente) throws ClienteException {
        return tienda.agregarCliente(cliente);
    }

    public boolean editarCliente(Cliente cliente, Cliente clienteSeleccionado) throws ClienteException {
        return tienda.editarCliente(cliente, clienteSeleccionado);
    }

    public boolean eliminarCliente(Cliente clienteSeleccionado) throws ClienteNoEncontradoException {
        return tienda.eliminarCliente(clienteSeleccionado.getNumIdentificacion());
    }

    public Cliente buscarCliente(String id) throws ClienteNoEncontradoException {
        return tienda.buscarCliente(id);
    }




    public List<Producto> obtenerListaProductos() {
        return new ArrayList<>(tienda.getListProductos().values()).stream().sorted().collect(Collectors.toList());
    }

    public boolean editarProducto(Producto producto, Producto productoSeleccionado) throws ProductoException {
        return tienda.editarProducto(producto,productoSeleccionado);
    }

    public boolean eliminarProducto(Producto productoSeleccionado) throws ProductoNoEncontradoException {
        return tienda.eliminarProducto(productoSeleccionado.getCodigo());
    }

    public Producto buscarProducto(String codigo) throws ProductoNoEncontradoException {
        return tienda.buscarProducto(codigo);
    }

    public boolean agregarProducto(Producto producto) throws ProductoException {
        return tienda.agregarProducto(producto);
    }

}