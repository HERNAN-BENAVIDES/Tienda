package co.edu.uniquindio.tienda.tienda.model;


import co.edu.uniquindio.tienda.tienda.exception.ClienteException;
import co.edu.uniquindio.tienda.tienda.exception.ClienteNoEncontradoException;
import co.edu.uniquindio.tienda.tienda.exception.ProductoException;
import co.edu.uniquindio.tienda.tienda.exception.ProductoNoEncontradoException;
import co.edu.uniquindio.tienda.tienda.model.services.ITienda;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


@SuppressWarnings("ALL")
@Data
public class Tienda implements ITienda {

    private final String nombre = "Tienda de la abuela";
    private final String direccion = "mi casa";
    private final String nit = "1010";

    private HashMap<String, Producto> listProductos;
    private HashMap<String, Cliente> listClientes;
    private LinkedList<Venta> historicoVentas;
    private TreeSet<Producto> inventarioProductos;

    public Tienda() {
        this.listProductos = new HashMap<>();
        this.listClientes = new HashMap<>();
        this.historicoVentas = new LinkedList<>();
        this.inventarioProductos = new TreeSet<>();
    }



    /*
    -----------------------------------------------------------------------------------------------------------
    ----------------------------METODOS GESTIONAR PRODUCTOS----------------------------------------------------
    */

    @Override
    public Boolean agregarProducto(Producto producto) throws ProductoException {
        if(listProductos.containsKey(producto.getCodigo())){
            throw new ProductoException("El codigo del producto ya se encuentra registrado");
        }else{
            listProductos.put(producto.getCodigo(), producto);
            return true;
        }
    }

    @Override
    public Boolean eliminarProducto(String codigoProducto) throws ProductoNoEncontradoException {
        if (listProductos.containsKey(codigoProducto)) {
            listProductos.remove(codigoProducto);
            return true;
        } else {
            throw new ProductoNoEncontradoException("El producto con el código " + codigoProducto + " no existe en el inventario.");
        }
    }

    @Override
    public Producto buscarProducto(String codigoProducto) throws ProductoNoEncontradoException {
        Producto producto = listProductos.get(codigoProducto);
        if (producto == null) {
            throw new ProductoNoEncontradoException("El producto con el código " + codigoProducto + " no existe en el inventario.");
        }
        return producto;
    }

    @Override
    public boolean editarProducto(Producto producto, Producto productoSeleccionado) throws ProductoException {
        if(listProductos.replace(productoSeleccionado.getCodigo(), productoSeleccionado, producto)){
            return true;
        }else {
            throw new ProductoException("Error al actualizar el  cliente");
        }
    }



    /*
    -----------------------------------------------------------------------------------------------------------
    ----------------------------METODOS GESTIONAR CLIENTES-----------------------------------------------------
    */

    @Override
    public Boolean agregarCliente(Cliente cliente) throws ClienteException {
        if (listClientes.containsKey(cliente.getNumIdentificacion())) {
            throw new ClienteException("El cliente con el ID " + cliente.getNumIdentificacion() + " ya está registrado.");
        } else {
            listClientes.put(cliente.getNumIdentificacion(), cliente);
            return true;
        }
    }

    @Override
    public Boolean eliminarCliente(String idCliente) throws ClienteNoEncontradoException {
        if (listClientes.containsKey(idCliente)) {
            listClientes.remove(idCliente);
            return true;
        } else {
            throw new ClienteNoEncontradoException("El cliente con el ID " + idCliente + " no existe.");
        }
    }

    @Override
    public boolean editarCliente(Cliente cliente, Cliente clienteSeleccionado) throws ClienteException {
        if(listClientes.replace(clienteSeleccionado.getNumIdentificacion(), clienteSeleccionado, cliente)){
            return true;
        }else {
            throw new ClienteException("Error al actualizar el  cliente");
        }
    }

    @Override
    public Cliente buscarCliente(String idCliente) throws ClienteNoEncontradoException {
        Cliente cliente = listClientes.get(idCliente);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("El cliente con el ID " + idCliente + " no existe.");
        }
        return cliente;
    }

    /*
    -----------------------------------------------------------------------------------------------------------
    ----------------------------METODOS GESTIONAR COMPRAS CLIENTES---------------------------------------------
    */

    @Override
    public List<Venta> obtenerVentasCliente(String idCliente) {
        return listClientes.get(idCliente).getListaCompras();
    }

    @Override
    public List<Venta> obtenerVentasPorFecha(Date fechaInicio, Date fechaFin) {

        LocalDate inicio = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fin = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return historicoVentas.stream()
                .filter(venta -> {
                    LocalDate fechaVenta = venta.getFecha();
                    return !fechaVenta.isBefore(inicio) && !fechaVenta.isAfter(fin);
                })
                .collect(Collectors.toList());
    }



    @Override
    public Boolean realizarVenta(Venta venta) {
        inventarioProductos = obtenerProductosConInventarioBajo();
        return agregarVentaAlHistorico(venta);
    }

    @Override
    public boolean agregarVentaAlHistorico(Venta venta) {
        return historicoVentas.add(venta);
    }

    @Override
    public List<Venta> obtenerHistoricoVentas() {
        return getHistoricoVentas();
    }

    @Override
    public TreeSet<Producto> obtenerProductosConInventarioBajo() {
        return new TreeSet<>(listProductos.values());
    }



}
