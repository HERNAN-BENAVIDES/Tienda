package co.edu.uniquindio.tienda.tienda.model.services;

import co.edu.uniquindio.tienda.tienda.exception.ClienteException;
import co.edu.uniquindio.tienda.tienda.exception.ClienteNoEncontradoException;
import co.edu.uniquindio.tienda.tienda.exception.ProductoException;
import co.edu.uniquindio.tienda.tienda.exception.ProductoNoEncontradoException;
import co.edu.uniquindio.tienda.tienda.model.Cliente;
import co.edu.uniquindio.tienda.tienda.model.Producto;
import co.edu.uniquindio.tienda.tienda.model.Venta;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;

@SuppressWarnings("ALL")
public interface ITienda {
    // Métodos para gestionar productos
    public Boolean agregarProducto(Producto producto) throws ProductoException;
    public Boolean eliminarProducto(String codigoProducto) throws ProductoNoEncontradoException;
    public Producto buscarProducto(String codigoProducto) throws ProductoNoEncontradoException;

    // Métodos para gestionar clientes
    public Boolean agregarCliente(Cliente cliente) throws ClienteException;
    public Boolean eliminarCliente(String idCliente) throws ClienteNoEncontradoException;
    public Cliente buscarCliente(String idCliente) throws ClienteNoEncontradoException;

    // Métodos para gestionar ventas
    public Boolean realizarVenta(Venta venta);
    public List<Venta> obtenerVentasCliente(String idCliente);
    public List<Venta> obtenerVentasPorFecha(Date fechaInicio, Date fechaFin);

    // Métodos para gestionar historial de ventas
    public boolean agregarVentaAlHistorico(Venta venta);
    public List<Venta> obtenerHistoricoVentas();

    // Métodos para gestionar inventario bajo
    public TreeSet<Producto> obtenerProductosConInventarioBajo();

    boolean editarCliente(Cliente cliente, Cliente clienteSeleccionado) throws ClienteException;

    boolean editarProducto(Producto producto, Producto productoSeleccionado) throws ProductoException;
}
