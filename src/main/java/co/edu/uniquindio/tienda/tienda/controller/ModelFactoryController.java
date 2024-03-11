package co.edu.uniquindio.tienda.tienda.controller;




import co.edu.uniquindio.tienda.tienda.exception.ClienteException;
import co.edu.uniquindio.tienda.tienda.exception.ClienteNoEncontradoException;
import co.edu.uniquindio.tienda.tienda.exception.ProductoException;
import co.edu.uniquindio.tienda.tienda.exception.ProductoNoEncontradoException;
import co.edu.uniquindio.tienda.tienda.model.*;
import co.edu.uniquindio.tienda.tienda.util.TiendaUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class ModelFactoryController {

    private Tienda tienda;

    public ModelFactoryController() {
        cargarDatosPrueba();
    }

    private void cargarDatosPrueba() {
        tienda = cargarProductos();
    }



    /**
     * Obtiene la lista de clientes de la tienda.
     *
     * @return Una lista de clientes de la tienda.
     */
    public List<Cliente> obtenerListaClientes() {
        return new ArrayList<>(tienda.getListClientes().values());
    }

    /**
     * Obtiene una lista de nombres de clientes disponibles para selección.
     *
     * @return Una lista de nombres de clientes disponibles para selección.
     */
    public List<String> obtenerListaClientesNombres() {
        return tienda.obtenerClientes();
    }

    /**
     * Agrega un cliente a la tienda.
     *
     * @param cliente El cliente a agregar.
     * @return true si el cliente se agregó correctamente, false de lo contrario.
     * @throws ClienteException Si hay un error al agregar el cliente.
     */
    public boolean agregarCliente(Cliente cliente) throws ClienteException {
        return tienda.agregarCliente(cliente);
    }

    /**
     * Edita un cliente existente en la tienda.
     *
     * @param cliente             El cliente con los datos actualizados.
     * @param clienteSeleccionado El cliente a editar.
     * @return true si el cliente se editó correctamente, false de lo contrario.
     * @throws ClienteException Si hay un error al editar el cliente.
     */
    public boolean editarCliente(Cliente cliente, Cliente clienteSeleccionado) throws ClienteException {
        return tienda.editarCliente(cliente, clienteSeleccionado);
    }

    /**
     * Elimina un cliente de la tienda.
     *
     * @param clienteSeleccionado El cliente a eliminar.
     * @return true si el cliente se eliminó correctamente, false de lo contrario.
     * @throws ClienteNoEncontradoException Si el cliente seleccionado no se encuentra en la tienda.
     */
    public boolean eliminarCliente(Cliente clienteSeleccionado) throws ClienteNoEncontradoException {
        return tienda.eliminarCliente(clienteSeleccionado.getNumIdentificacion());
    }

    /**
     * Busca un cliente en la tienda por su ID.
     *
     * @param id El ID del cliente a buscar.
     * @return El cliente encontrado.
     * @throws ClienteNoEncontradoException Si el cliente con el ID especificado no se encuentra en la tienda.
     */
    public Cliente buscarCliente(String id) throws ClienteNoEncontradoException {
        return tienda.buscarCliente(id);
    }

    /**
     * Obtiene la lista de productos de la tienda ordenada alfabéticamente por nombre.
     *
     * @return Una lista de productos de la tienda ordenada alfabéticamente por nombre.
     */
    public List<Producto> obtenerListaProductos() {
        return new ArrayList<>(tienda.obtenerProductosConInventarioBajo());
    }

    /**
     * Edita un producto existente en la tienda.
     *
     * @param producto             El producto con los datos actualizados.
     * @param productoSeleccionado El producto a editar.
     * @return true si el producto se editó correctamente, false de lo contrario.
     * @throws ProductoException Si hay un error al editar el producto.
     */
    public boolean editarProducto(Producto producto, Producto productoSeleccionado) throws ProductoException {
        return tienda.editarProducto(producto, productoSeleccionado);
    }

    /**
     * Elimina un producto de la tienda.
     *
     * @param productoSeleccionado El producto a eliminar.
     * @return true si el producto se eliminó correctamente, false de lo contrario.
     * @throws ProductoNoEncontradoException Si el producto seleccionado no se encuentra en la tienda.
     */
    public boolean eliminarProducto(Producto productoSeleccionado) throws ProductoNoEncontradoException {
        return tienda.eliminarProducto(productoSeleccionado.getCodigo());
    }

    /**
     * Busca un producto en la tienda por su código.
     *
     * @param codigo El código del producto a buscar.
     * @return El producto encontrado.
     * @throws ProductoNoEncontradoException Si el producto con el código especificado no se encuentra en la tienda.
     */
    public Producto buscarProducto(String codigo) throws ProductoNoEncontradoException {
        return tienda.buscarProducto(codigo);
    }

    /**
     * Agrega un nuevo producto a la tienda.
     *
     * @param producto El producto a agregar.
     * @return true si el producto se agregó correctamente, false de lo contrario.
     * @throws ProductoException Si hay un error al agregar el producto.
     */
    public boolean agregarProducto(Producto producto) throws ProductoException {
        return tienda.agregarProducto(producto);
    }

    /**
     * Obtiene la lista de ventas en el historial de la tienda.
     *
     * @return Una lista de ventas en el historial de la tienda.
     */
    public List<Venta> obtenerListaVentas() {
        return tienda.obtenerHistoricoVentas();
    }

    /**
     * Busca una venta en el historial de ventas por su código.
     *
     * @param codigo El código de la venta a buscar.
     * @return La venta encontrada, o null si no se encuentra ninguna venta con el código especificado.
     */
    public Venta buscarVenta(String codigo) {
        return tienda.buscarVenta(codigo);
    }

    /**
     * Obtiene un producto de la tienda por su código.
     *
     * @param codigoProducto El código del producto a obtener.
     * @return El producto correspondiente al código especificado, o null si no se encuentra.
     */
    public Producto obtenerProductoPorCodigo(String codigoProducto) {
        return tienda.obtenerProducto(codigoProducto);
    }

    /**
     * Agrega un producto al carrito de compras con la cantidad especificada.
     *
     * @param codigoProducto El código del producto a agregar al carrito.
     * @param cantidad       La cantidad del producto a agregar al carrito.
     */
    public void agregarProductoCarrito(String codigoProducto, int cantidad) {
        tienda.agregarProductoCarrito(codigoProducto,cantidad);
    }

    /**
     * Obtiene el contenido actual del carrito de compras.
     *
     * @return Un mapa que contiene los productos en el carrito y sus cantidades.
     */
    public HashMap<String,Integer> obtenerCarrito() {
        return tienda.obtenerCarrito();
    }

    /**
     * Agrega una venta al historial de ventas de la tienda.
     *
     * @param venta La venta a agregar al historial.
     * @return true si la venta se agregó correctamente al historial, false de lo contrario.
     */
    public boolean agregarVenta(Venta venta) {
        return tienda.agregarVentaAlHistorico(venta);
    }

    /**
     * Vacía el contenido del carrito de compras.
     */
    public void vaciarCarrito() {
        tienda.vaciarCarrito();
    }

    /**
     * Actualiza el inventario de productos de la tienda según los detalles de una venta.
     *
     * @param detallesVenta Los detalles de la venta que afectarán al inventario.
     */
    public void actulizarInventario(List<DetalleVenta> detallesVenta) {
        tienda.actualizarInventario(detallesVenta);
    }
    /*
    -----------------------------------------------------------------------------------------------------------
    --------------------------------------GET INSTANCE---------------------------------------------------------
    */
    /**
     * Obtiene una instancia única de la clase ModelFactoryController.
     * @return Una instancia única de ModelFactoryController.
     */
    public static ModelFactoryController getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * Clase interna que contiene la instancia única de ModelFactoryController.
     */
    private static class SingletonHolder {
        private static final ModelFactoryController INSTANCE = new ModelFactoryController();
    }

    /*
    -----------------------------------------------------------------------------------------------------------
    -----------------------------------------------------------------------------------------------------------
    */


    /**
     * Carga los productos y clientes desde archivos de texto y los devuelve en una instancia de Tienda.
     *
     * @return Una instancia de Tienda con los productos y clientes cargados desde archivos de texto.
     */
    private Tienda cargarProductos() {
        Tienda tienda = new Tienda();
        HashMap<String,Producto> productos = new HashMap<>();

        // Cargar productos desde el archivo productos.txt
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/co/edu/uniquindio/tienda/tienda/txt/productos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                String nombre = partes[0].trim();
                String codigo = partes[1].trim();
                double precio = Double.parseDouble(partes[2].trim());
                int cantidad = Integer.parseInt(partes[3].trim());
                Producto producto = new Producto(nombre, codigo, precio, cantidad);
                productos.put(codigo, producto);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de productos: " + e.getMessage());
        } finally {
            tienda.setListProductos(productos);
        }

        HashMap<String,Cliente> clientes = new HashMap<>();

        // Cargar clientes desde el archivo clientes.txt
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/co/edu/uniquindio/tienda/tienda/txt/clientes.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) { // nombre, código, precio, cantidad
                    String nombre = partes[0].trim();
                    String id = partes[1].trim();
                    String direccion = partes[2].trim();
                    Cliente cliente = new Cliente(nombre, id, direccion);
                    clientes.put(id,cliente);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de clientes: " + e.getMessage());
        } finally {
            tienda.setListClientes(clientes);
        }

        return tienda;
    }


}