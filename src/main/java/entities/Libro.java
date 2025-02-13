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
    private String Isbn;

    @ManyToOne
    @JoinColumn(name = "Id_Autor")
    private Autor autor;  // Cambiar de Integer a Autor

    private String editorial;
    private Integer fechapublicacion;


    public Libro() {
    }

    public Libro(Integer id, String titulo, String isbn, Autor autor, String editorial, Integer fechapublicacion) {
        this.id = id;
        this.titulo = titulo;
        Isbn = isbn;
        this.autor = autor;
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

    public String getIsbn() {
        return Isbn;
    }

    public void setIsbn(String isbn) {
        Isbn = isbn;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
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
        return titulo + " // " + autor;
    }

}
