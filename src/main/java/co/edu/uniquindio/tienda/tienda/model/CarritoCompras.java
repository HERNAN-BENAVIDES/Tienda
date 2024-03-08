package co.edu.uniquindio.tienda.tienda.model;


import co.edu.uniquindio.tienda.tienda.exception.ProductoException;
import co.edu.uniquindio.tienda.tienda.exception.VaciarCarritoComprasException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Random;

@Data
@AllArgsConstructor
@SuppressWarnings("ALL")
//"Besos"
public class CarritoCompras {

    public String codigo;
    public HashSet<String> productosCarrito;

    public CarritoCompras() {
        this.codigo = generarCodigo();
        this.productosCarrito = new HashSet<>();
    }

    private String generarCodigo() {
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        // Generar 4 letras
        for (int i = 0; i < 4; i++) {
            codigo.append(letras.charAt(random.nextInt(letras.length())));
        }

        // Generar 2 números
        for (int i = 0; i < 2; i++) {
            codigo.append(random.nextInt(10));
        }

        return codigo.toString();
    }

    // Método para agregar un producto al carrito
    public Boolean agregarProducto(String codigoProducto) throws ProductoException {
        if(productosCarrito.add(codigoProducto)){
            return true;
        }else {
            throw new ProductoException("Error al  agregar el producto");
        }
    }

    // Método para eliminar un producto del carrito
    public Boolean eliminarProducto(String codigoProducto) throws ProductoException {
        if(productosCarrito.remove(codigoProducto)){
            return true;
        }else {
            throw new ProductoException("Error al  eliminar el producto");
        }
    }

    // Método para vaciar el carrito
    public Boolean vaciarCarrito() throws VaciarCarritoComprasException {
        productosCarrito.clear();

        if (productosCarrito.isEmpty()){
            return true;
        }else{
            throw new VaciarCarritoComprasException("Error al eliminar los elementos del carrito");
        }
    }
}
