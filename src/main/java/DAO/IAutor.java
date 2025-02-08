package DAO;

import entities.Autor;
import entities.Libro;

import java.util.List;

public interface IAutor {

    List<Autor> findAll();

    List<Autor> findByNombre(String Nombre);

    Autor save(Autor Autor);

    Autor update(Autor Autor);

    boolean delete(Autor Autor);
}
