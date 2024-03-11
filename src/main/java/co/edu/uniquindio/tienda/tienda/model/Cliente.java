package co.edu.uniquindio.tienda.tienda.model;

import co.edu.uniquindio.tienda.tienda.exception.ProductoException;
import co.edu.uniquindio.tienda.tienda.exception.VaciarCarritoComprasException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@SuppressWarnings("ALL")
public class Cliente {

    private String nombre;
    private String numIdentificacion;
    private String direccion;

    private List<Venta> listaCompras;

    public Cliente(String nombre, String numIdentificacion, String direccion) {
        this.nombre = nombre;
        this.numIdentificacion = numIdentificacion;
        this.direccion = direccion;
        this.listaCompras = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente)) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(getNombre(), cliente.getNombre()) && Objects.equals(getNumIdentificacion(), cliente.getNumIdentificacion()) && Objects.equals(getDireccion(), cliente.getDireccion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNombre(), getNumIdentificacion(), getDireccion());
    }
}
