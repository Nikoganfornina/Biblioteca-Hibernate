package DAO;

import entities.Libro;
import org.hibernate.annotations.processing.Find;

import java.util.List;

public interface Ilibro {

    List<Libro> findAll();

    List<Libro> findByTitulo(String titulo);

    List<Libro> findByAutor(Integer autor);

    List<Libro> findByISBN(Integer isbn);

    Libro save(Libro libro);

    Libro update(Libro libro);

    boolean delete(Libro libro);

}
