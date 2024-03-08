package co.edu.uniquindio.tienda.tienda.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("ALL")
public class Producto implements Comparable<Producto> {

    private String nombre;
    private String codigo;
    private double precio;
    private int cantidadInventario;



    @Override
    public int compareTo(Producto o) {
        return Integer.compare(getCantidadInventario(), o.getCantidadInventario());
    }

}
