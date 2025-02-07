package entities;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.io.Serializable;
import java.util.ListResourceBundle;

@Entity
@Table(name = "Libro")
public class Libro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private Integer Isbn;

    @ManyToOne
    @JoinColumn(name = "Id_Autor")
    private Integer Id_Autor;

    private String editorial;
    private Integer fechapublicacion;


    public Libro() {
    }

    public Libro(Integer id, String titulo, Integer isbn, Integer autor, String editorial, Integer fechapublicacion) {
        this.id = id;
        this.titulo = titulo;
        Isbn = isbn;
        this.Id_Autor = autor;
        this.editorial = editorial;
        this.fechapublicacion = fechapublicacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Integer getIsbn() {
        return Isbn;
    }

    public void setIsbn(Integer isbn) {
        Isbn = isbn;
    }


    public Integer getAutor() {
        return Id_Autor;
    }

    public void setAutor(Integer autor) {
        this.Id_Autor = autor;
    }


    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getFechapublicacion() {
        return fechapublicacion;
    }

    public void setFechapublicacion(Integer fechapublicacion) {
        this.fechapublicacion = fechapublicacion;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", ISBN=" + Isbn +
                ", autor='" + Id_Autor + '\'' +
                ", editorial='" + editorial + '\'' +
                ", fechapublicacion=" + fechapublicacion +
                '}';
    }
}
