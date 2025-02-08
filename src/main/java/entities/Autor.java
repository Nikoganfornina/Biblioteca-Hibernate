package entities;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "Autor")
public class Autor implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String Nombre;
    private String Nacionalidad;

    public Autor() {
    }
    public Autor(Integer id, String nombre, String nacionalidad) {
        this.id = id;
        Nombre = nombre;
        Nacionalidad = nacionalidad;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getNacionalidad() {
        return Nacionalidad;
    }
    public void setNacionalidad(String nacionalidad) {
        Nacionalidad = nacionalidad;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", Nombre='" + Nombre + '\'' +
                ", Nacionalidad='" + Nacionalidad + '\'' +
                '}';
    }



}
