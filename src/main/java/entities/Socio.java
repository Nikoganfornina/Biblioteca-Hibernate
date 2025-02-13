package entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "socios")
public class Socio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String nombre;


    private String direccion;


    private Integer telefono;

    // Constructor vacío
    public Socio() {
    }

    // Constructor completo
    public Socio(String nombre, String direccion, Integer telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    // Método toString
    @Override
    public String toString() {
        return  "ID:" + id + " Nombre:" + nombre + " Dirección:" + direccion + " Teléfono:" + telefono;
    }
}
