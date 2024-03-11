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
    private HashMap<String, Integer> productosCarrito;
    private LinkedList<Venta> historicoVentas;
    private TreeSet<Producto> inventarioProductos;

    public Tienda() {
        this.listProductos = new HashMap<>();
        this.listClientes = new HashMap<>();
        this.historicoVentas = new LinkedList<>();
        this.inventarioProductos = new TreeSet<>();
        this.productosCarrito = new HashMap<>();
    }



    /*
    -----------------------------------------------------------------------------------------------------------
    ----------------------------METODOS GESTIONAR PRODUCTOS----------------------------------------------------
    */

    /**
     * Agrega un producto al inventario.
     *
     * @param producto El producto a agregar.
     * @return true si el producto se agregó correctamente, false de lo contrario.
     * @throws ProductoException Si el código del producto ya se encuentra registrado en el inventario.
     */
    @Override
    public Boolean agregarProducto(Producto producto) throws ProductoException {
        if (listProductos.containsKey(producto.getCodigo())) {
            throw new ProductoException("El codigo del producto ya se encuentra registrado");
        } else {
            listProductos.put(producto.getCodigo(), producto);
            return true;
        }
    }

    /**
     * Elimina un producto del inventario.
     *
     * @param codigoProducto El código del producto a eliminar.
     * @return true si el producto se eliminó correctamente, false de lo contrario.
     * @throws ProductoNoEncontradoException Si el producto con el código especificado no existe en el inventario.
     */
    @Override
    public Boolean eliminarProducto(String codigoProducto) throws ProductoNoEncontradoException {
        if (listProductos.containsKey(codigoProducto)) {
            listProductos.remove(codigoProducto);
            return true;
        } else {
            throw new ProductoNoEncontradoException("El producto con el código " + codigoProducto + " no existe en el inventario.");
        }
    }

    /**
     * Busca un producto en el inventario según su código.
     *
     * @param codigoProducto El código del producto a buscar.
     * @return El producto encontrado.
     * @throws ProductoNoEncontradoException Si el producto con el código especificado no existe en el inventario.
     */
    @Override
    public Producto buscarProducto(String codigoProducto) throws ProductoNoEncontradoException {
        Producto producto = listProductos.get(codigoProducto);
        if (producto == null) {
            throw new ProductoNoEncontradoException("El producto con el código " + codigoProducto + " no existe en el inventario.");
        }
        return producto;
    }

    /**
     * Edita un producto en el inventario.
     *
     * @param producto             El nuevo producto con los datos actualizados.
     * @param productoSeleccionado El producto a editar.
     * @return true si el producto se editó correctamente, false de lo contrario.
     * @throws ProductoException Si hay un error al actualizar el producto.
     */
    @Override
    public boolean editarProducto(Producto producto, Producto productoSeleccionado) throws ProductoException {
        if (listProductos.replace(productoSeleccionado.getCodigo(), productoSeleccionado, producto)) {
            return true;
        } else {
            throw new ProductoException("Error al actualizar el producto");
        }
    }

    /*
    -----------------------------------------------------------------------------------------------------------
    ----------------------------METODOS GESTIONAR CLIENTES-----------------------------------------------------
    */

    /**
     * Agrega un cliente al sistema.
     *
     * @param cliente El cliente a agregar.
     * @return true si el cliente se agregó correctamente, false de lo contrario.
     * @throws ClienteException Si el cliente con el mismo número de identificación ya está registrado.
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

    /**
     * Elimina un cliente del sistema.
     *
     * @param idCliente El ID del cliente a eliminar.
     * @return true si el cliente se eliminó correctamente, false de lo contrario.
     * @throws ClienteNoEncontradoException Si el cliente con el ID especificado no existe.
     */
    @Override
    public Boolean eliminarCliente(String idCliente) throws ClienteNoEncontradoException {
        if (listClientes.containsKey(idCliente)) {
            listClientes.remove(idCliente);
            return true;
        } else {
            throw new ClienteNoEncontradoException("El cliente con el ID " + idCliente + " no existe.");
        }
    }

    /**
     * Edita los datos de un cliente en el sistema.
     *
     * @param cliente             El cliente con los datos actualizados.
     * @param clienteSeleccionado El cliente a editar.
     * @return true si el cliente se editó correctamente, false de lo contrario.
     * @throws ClienteException Si hay un error al actualizar el cliente.
     */
    @Override
    public boolean editarCliente(Cliente cliente, Cliente clienteSeleccionado) throws ClienteException {
        if (listClientes.replace(clienteSeleccionado.getNumIdentificacion(), clienteSeleccionado, cliente)) {
            return true;
        } else {
            throw new ClienteException("Error al actualizar el cliente");
        }
    }

    /**
     * Busca un cliente en el sistema según su ID.
     *
     * @param idCliente El ID del cliente a buscar.
     * @return El cliente encontrado.
     * @throws ClienteNoEncontradoException Si el cliente con el ID especificado no existe.
     */
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
    ----------------------------METODOS GESTIONAR VENTAS---------------------------------------------
    */

    /**
     * Obtiene la lista de ventas realizadas a un cliente específico.
     *
     * @param idCliente El ID del cliente del cual se desean obtener las ventas.
     * @return La lista de ventas del cliente especificado.
     */
    @Override
    public List<Venta> obtenerVentasCliente(String idCliente) {
        return listClientes.get(idCliente).getListaCompras();
    }

    /**
     * Realiza una venta y la agrega al historial de ventas.
     *
     * @param venta La venta a realizar.
     * @return true si la venta se realizó correctamente, false de lo contrario.
     */
    @Override
    public Boolean realizarVenta(Venta venta) {
        inventarioProductos = obtenerProductosConInventarioBajo();
        return agregarVentaAlHistorico(venta);
    }

    /**
     * Agrega una venta al historial de ventas.
     *
     * @param venta La venta a agregar.
     * @return true si la venta se agregó correctamente al historial, false de lo contrario.
     */
    @Override
    public boolean agregarVentaAlHistorico(Venta venta) {
        return historicoVentas.add(venta);
    }

    /**
     * Obtiene el historial de ventas ordenado por fecha en orden descendente.
     *
     * @return La lista de ventas del historial, ordenada por fecha de manera descendente.
     */
    @Override
    public List<Venta> obtenerHistoricoVentas() {
        return getHistoricoVentas().stream().sorted().toList().reversed();
    }


    /**
     * Busca una venta en el historial de ventas por su código.
     *
     * @param codigo El código de la venta a buscar.
     * @return La venta encontrada, o null si no se encuentra ninguna venta con el código especificado.
     */
    public Venta buscarVenta(String codigo) {
        return (Venta) historicoVentas.stream()
                .filter(venta -> venta.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }


    /*
    -----------------------------------------------------------------------------------------------------------
    ----------------------------OTROS METODOS---------------------------------------------
    */


    /**
     * Obtiene un conjunto ordenado de productos con inventario bajo.
     *
     * @return Un conjunto ordenado de productos con inventario bajo.
     */
    @Override
    public TreeSet<Producto> obtenerProductosConInventarioBajo() {
        return new TreeSet<>(listProductos.values());
    }

    /**
     * Obtiene una lista de nombres de clientes disponibles para selección.
     *
     * @return Una lista de nombres de clientes disponibles para selección.
     */
    public List<String> obtenerClientes() {
        List<Cliente> listClientes = new ArrayList<>(getListClientes().values());
        List<String> list = new ArrayList<>();
        list.add("Selecciona");
        for(Cliente cliente: listClientes){
            list.add(cliente.getNombre());
        }
        return list;
    }

    /**
     * Obtiene un producto por su código.
     *
     * @param codigoProducto El código del producto a obtener.
     * @return El producto correspondiente al código especificado, o null si no se encuentra.
     */
    public Producto obtenerProducto(String codigoProducto) {
        List<Producto> list = new ArrayList<>(listProductos.values());

        for(Producto producto: list){
            if (producto.getCodigo().equals(codigoProducto)){
                return producto;
            }
        }

        return null;
    }

    /**
     * Agrega un producto al carrito de compras con la cantidad especificada.
     *
     * @param codigoProducto El código del producto a agregar al carrito.
     * @param cantidad       La cantidad del producto a agregar al carrito.
     */
    public void agregarProductoCarrito(String codigoProducto, int cantidad) {
        productosCarrito.put(codigoProducto,cantidad);
    }

    /**
     * Obtiene el contenido actual del carrito de compras.
     *
     * @return Un mapa que contiene los productos en el carrito y sus cantidades.
     */
    public HashMap<String,Integer> obtenerCarrito() {
        return productosCarrito;
    }

    /**
     * Vacía el contenido del carrito de compras.
     */
    public void vaciarCarrito() {
        productosCarrito.clear();
    }

    /**
     * Actualiza el inventario de productos según los detalles de una venta.
     *
     * @param detallesVenta Los detalles de la venta que afectarán al inventario.
     */
    public void actualizarInventario(List<DetalleVenta> detallesVenta) {
        for (DetalleVenta detalleVenta: detallesVenta){
            Producto producto = detalleVenta.getProducto();
            int cantidadVendida = detalleVenta.getCantidad();
            int cantidadActual = producto.getCantidadInventario();
            producto.setCantidadInventario(cantidadActual - cantidadVendida);
        }
    }

}
