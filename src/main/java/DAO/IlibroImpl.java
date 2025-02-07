package DAO;

import entities.Libro;

import java.util.List;

public class IlibroImpl implements Ilibro {
    @Override
    public List<Libro> findAll() {
        return List.of();
    }

    @Override
    public List<Libro> findByTitulo(String titulo) {
        return List.of();
    }

    @Override
    public List<Libro> findByAutor(String titulo) {
        return List.of();
    }

    @Override
    public List<Libro> findByISBN(Integer isbn) {
        return List.of();
    }

    @Override
    public Libro save(Libro libro) {
        return null;
    }

    @Override
    public Libro update(Libro libro) {
        return null;
    }

    @Override
    public boolean delete(Libro libro) {
        return false;
    }
}
